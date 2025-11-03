package com.example.proyectologin005d.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyectologin005d.ui.pages.CarritoScreen
import com.example.proyectologin005d.ui.pages.ContactoScreen
import com.example.proyectologin005d.ui.pages.IndexScreen
import com.example.proyectologin005d.ui.pages.Nosotros
import com.example.proyectologin005d.ui.pages.PerfilScreen
import com.example.proyectologin005d.ui.pages.ProductosScreen

sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Index : Screen("index", "Inicio", { Icon(Icons.Default.Home, contentDescription = null) })
    object Nosotros : Screen("nosotros", "Nosotros", { Icon(Icons.Default.Info, contentDescription = null) })
    object Contacto : Screen("contactanos", "Contacto", { Icon(Icons.Default.Mail, contentDescription = null) })
    object Productos : Screen("productos", "Productos", { Icon(Icons.Default.Star, contentDescription = null) })
    object Carrito : Screen("carrito", "Carrito", { Icon(Icons.Default.ShoppingCart, contentDescription = null) })
    object Perfil : Screen("perfil", "Perfil", { Icon(Icons.Default.Person, contentDescription = null) })
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val screens = listOf(
        Screen.Index,
        Screen.Nosotros,
        Screen.Contacto,
        Screen.Productos,
        Screen.Carrito,
        Screen.Perfil
    )

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = screen.icon,
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "index",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("index") { IndexScreen(navController) }
            composable("nosotros") { Nosotros() }
            composable("contactanos") { ContactoScreen() }
            composable("productos") { ProductosScreen() }
            composable("carrito") { CarritoScreen() }
            composable("perfil") { PerfilScreen() }
        }
    }
}
