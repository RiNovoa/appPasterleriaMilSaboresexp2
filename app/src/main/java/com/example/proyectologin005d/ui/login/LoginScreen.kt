package com.example.proyectologin005d.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectologin005d.data.repository.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val authRepository = remember { AuthRepository(context) }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Si ya hay sesión, salta a index
    LaunchedEffect(Unit) {
        val sessionUsername = authRepository.getSessionUsername()
        if (sessionUsername != null) {
            navController.navigate("index") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // Fondo crema con una banda rosa suave arriba
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(MaterialTheme.colorScheme.secondary, Color(0xFFFFD6E1))
                    )
                )
        )

        // Tarjeta central
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = "file:///android_asset/img/logo.png"),
                    contentDescription = "Logo Pastelería",
                    modifier = Modifier.height(80.dp)
                )
                Spacer(Modifier.height(16.dp))

                // Título estilo Pacifico
                Text(
                    text = "Pastelería 1000 Sabores",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Celebra la dulzura de la vida",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                // Email
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // Password con mostrar/ocultar
                var showPass by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPass = !showPass }) {
                            Icon(
                                imageVector = if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showPass) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    }
                )

                if (error != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = error ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(18.dp))

                // Botón principal (Chocolate)
                Button(
                    onClick = {
                        scope.launch {
                            val success = authRepository.login(correo, contrasena)
                            if (success) {
                                navController.navigate("index") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                error = "Usuario o contraseña incorrectos"
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Iniciar sesión", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(Modifier.height(12.dp))

                // Link a registro
                OutlinedButton(
                    onClick = { navController.navigate("register") },
                    shape = RoundedCornerShape(16.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Crear cuenta", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    "Registro con beneficios:\n• 50% 3ra edad (50+)\n• 10% de por vida con código FELICES50\n• Cumpleaños gratis con correo Duoc",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}