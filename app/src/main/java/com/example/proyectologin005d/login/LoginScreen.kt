package com.example.proyectologin005d.login

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectologin005d.ui.login.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val ui = viewModel.ui.collectAsState().value
    val cs = MaterialTheme.colorScheme
    val ty = MaterialTheme.typography

    // Si ya hay sesión, salta a index
    LaunchedEffect(Unit) {
        viewModel.checkSession { _ ->
            navController.navigate("index") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // Fondo crema con una banda rosa suave arriba
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(cs.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(cs.secondary, Color(0xFFFFD6E1))
                    )
                )
        )

        // Tarjeta central
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = cs.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título estilo Pacifico
                Text(
                    text = "Pastelería 1000 Sabores",
                    style = ty.headlineLarge,
                    color = cs.primary
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Celebra la dulzura de la vida",
                    style = ty.bodySmall,
                    color = cs.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                // Email
                OutlinedTextField(
                    value = ui.username,
                    onValueChange = viewModel::onUsernameChange,
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
                    value = ui.password,
                    onValueChange = viewModel::onPasswordChange,
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

                if (ui.error != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = ui.error ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = ty.bodySmall
                    )
                }

                Spacer(Modifier.height(18.dp))

                // Botón principal (Chocolate)
                Button(
                    onClick = {
                        viewModel.submit {
                            navController.navigate("index") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    },
                    enabled = !ui.isLoading,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = cs.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Iniciar sesión", style = ty.titleMedium, color = cs.onPrimary)
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
                        contentColor = cs.primary
                    )
                ) {
                    Text("Crear cuenta", style = ty.titleMedium)
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    "Registro con beneficios:\n• 50% 3ra edad (50+)\n• 10% de por vida con código FELICES50\n• Cumpleaños gratis con correo Duoc",
                    style = ty.bodySmall,
                    color = cs.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
