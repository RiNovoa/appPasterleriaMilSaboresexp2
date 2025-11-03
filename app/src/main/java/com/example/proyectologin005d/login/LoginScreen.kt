package com.example.proyectologin005d.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectologin005d.R
import com.example.proyectologin005d.ui.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = viewModel()
) {
    val state = vm.ui.collectAsState().value  // si tu VM expone uiState(), deja vm.uiState
    var showPass by remember { mutableStateOf(false) }

    // ⚡️ Al abrir la pantalla: si ya hay sesión, navega directo
    LaunchedEffect(Unit) {
        vm.checkSession { user ->
            navController.navigate("DrawerMenu/$user") {
                popUpTo("login") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    val colorScheme = darkColorScheme(
        primary = Color(0xFF98222E),
        onPrimary = Color.White,
        onSurface = Color(0xFF333333),
    )

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Mi Primer App",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color(0xFFF0F0F0)),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bienvenido !",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Image(
                    painter = painterResource(id = R.drawable.logoduoc),
                    contentDescription = "Logo App",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(66.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "texto uno",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        "texto dos",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                OutlinedTextField(
                    value = state.username,
                    onValueChange = vm::onUsernameChange,
                    label = { Text("Usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.95f)
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { showPass = !showPass }) {
                            Text(if (showPass) "Ocultar" else "Ver")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.95f)
                )

                if (state.error != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.error ?: " ",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(66.dp))

                Button(
                    onClick = {
                        vm.submit { user ->
                            navController.navigate("DrawerMenu/$user") {
                                popUpTo("login") { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text(if (state.isLoading) "Validando" else "Iniciar Sesión")
                }

                // Enlace a registro (si tienes ruta "register")
                TextButton(onClick = { navController.navigate("register") }) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }
        }
    }
}
