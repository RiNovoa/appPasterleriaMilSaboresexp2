package com.example.proyectologin005d.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectologin005d.data.model.User
import com.example.proyectologin005d.data.repository.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController,
    repo: AuthRepository
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registrar nuevo usuario", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Usuario") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })

        Button(
            onClick = {
                scope.launch {
                    loading = true
                    val ok = repo.register(
                        User(username = username.trim(), password = password, nombre = nombre, email = email)
                    )
                    loading = false
                    if (ok) {
                        // tras registrarse, auto-login y navega igual que login
                        navController.navigate("DrawerMenu/$username") {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        msg = "Usuario ya existe"
                    }
                }
            },
            enabled = !loading
        ) { Text(if (loading) "Creando..." else "Registrar") }

        if (msg.isNotBlank()) Text(msg)

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver al login")
        }
    }
}
