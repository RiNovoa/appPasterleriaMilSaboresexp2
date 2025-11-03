package com.example.proyectologin005d.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Pastelería • Hola $username") }
            )
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Sección Inicio
            item {
                Text("Inicio", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                ListItem(
                    headlineContent = { Text("Ir al inicio") },
                    leadingContent = { Icon(Icons.Filled.Home, contentDescription = null) },
                    modifier = Modifier.fillMaxSize()
                )
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
            }

            // Sección Productos
            item {
                Text("Productos", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                ListItem(
                    headlineContent = { Text("Tortas y Pasteles") },
                    leadingContent = { Icon(Icons.Filled.Cake, contentDescription = null) },
                    modifier = Modifier.fillMaxSize()
                )
                ListItem(
                    headlineContent = { Text("Cafetería") },
                    leadingContent = { Icon(Icons.Filled.LocalCafe, contentDescription = null) },
                    modifier = Modifier.fillMaxSize()
                )
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
            }

            // Sección Pedidos
            item {
                Text("Pedidos", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                ListItem(
                    headlineContent = { Text("Mi carrito") },
                    leadingContent = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
