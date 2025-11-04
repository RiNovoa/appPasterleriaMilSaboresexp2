package com.example.proyectologin005d.ui.pages

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyectologin005d.R
import com.example.proyectologin005d.data.model.User
import com.example.proyectologin005d.data.repository.AuthRepository
import kotlinx.coroutines.launch
import java.io.File

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController
) {
    val cs = MaterialTheme.colorScheme
    val ty = MaterialTheme.typography
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val authRepository = remember { AuthRepository(context) }
    var user by remember { mutableStateOf<User?>(null) }

    var fotoUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    LaunchedEffect(Unit) {
        val username = authRepository.getSessionUsername()
        if (username != null) {
            user = authRepository.getUserByUsername(username)
            val savedUri = loadPhotoUriForUser(context, username)
            if (savedUri != null) {
                fotoUri = Uri.parse(savedUri)
            }
        }
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                fotoUri?.let { uri ->
                    user?.correo?.let { username ->
                        savePhotoUriForUser(context, username, uri.toString())
                    }
                }
            }
        }
    )

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val newUri = createImageUri(context)
                fotoUri = newUri
                takePictureLauncher.launch(newUri)
            } else {
                Toast.makeText(context, "Se requiere permiso de cámara", Toast.LENGTH_SHORT).show()
            }
        }
    )

    fun launchCamera() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                val newUri = createImageUri(context)
                fotoUri = newUri
                takePictureLauncher.launch(newUri)
            }
            else -> requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val pickFromGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                fotoUri = uri
                user?.correo?.let { username ->
                    savePhotoUriForUser(context, username, uri.toString())
                }
            }
        }
    )

    fun launchGallery() {
        pickFromGalleryLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    var showSheet by remember { mutableStateOf(false) }
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Cambiar foto de perfil", style = ty.titleMedium)
                Spacer(Modifier.height(8.dp))

                ListItem(
                    leadingContent = { Icon(Icons.Outlined.PhotoCamera, null) },
                    headlineContent = { Text("Tomar foto") },
                    supportingContent = { Text("Usar la cámara del dispositivo") },
                    modifier = Modifier.fillMaxWidth().clickable { showSheet = false; launchCamera() }
                )
                ListItem(
                    leadingContent = { Icon(Icons.Outlined.PhotoLibrary, null) },
                    headlineContent = { Text("Elegir de galería") },
                    supportingContent = { Text("Seleccionar una imagen del dispositivo") },
                    modifier = Modifier.fillMaxWidth().clickable { showSheet = false; launchGallery() }
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }

    Scaffold(
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = {
                        scope.launch {
                            authRepository.logout()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp).height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = cs.secondary, contentColor = cs.onSecondary)
                ) {
                    Text("Cerrar sesión", style = ty.titleMedium)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = fotoUri ?: R.drawable.ic_default_profile,
                contentDescription = "Foto de perfil",
                modifier = Modifier.size(144.dp).clip(CircleShape).border(3.dp, cs.primary, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(14.dp))

            OutlinedButton(onClick = { showSheet = true }, modifier = Modifier.height(40.dp)) { Text("Cambiar foto") }

            Spacer(Modifier.height(18.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(containerColor = cs.surfaceContainerHigh)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = user?.nombre ?: "Usuario",
                        style = ty.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = user?.correo ?: "correo@ejemplo.com",
                        style = ty.bodyLarge,
                        color = cs.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Spacer(Modifier.weight(1f))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(containerColor = cs.surfaceContainerLow)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Beneficios activos", style = ty.titleMedium, color = cs.primary)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "• 10% de descuento permanente (FELICES50)\n" +
                        "• Regalo de cumpleaños con correo Duoc",
                        style = ty.bodyMedium,
                        color = cs.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val dir = File(context.filesDir, "photos").apply { mkdirs() }
    val file = File(dir, "perfil_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}

private fun savePhotoUriForUser(context: Context, username: String, uri: String) {
    context.getSharedPreferences("perfil_photos", Context.MODE_PRIVATE).edit().putString("photo_$username", uri).apply()
}

private fun loadPhotoUriForUser(context: Context, username: String): String? {
    return context.getSharedPreferences("perfil_photos", Context.MODE_PRIVATE).getString("photo_$username", null)
}
