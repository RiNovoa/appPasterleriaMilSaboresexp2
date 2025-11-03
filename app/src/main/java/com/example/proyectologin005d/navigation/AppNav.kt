package com.example.proyectologin005d.navigation

import android.net.Uri
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectologin005d.data.repository.AuthRepository
import com.example.proyectologin005d.login.LoginScreen
import com.example.proyectologin005d.ui.login.RegisterScreen
import com.example.proyectologin005d.ui.pages.CarritoScreen
import com.example.proyectologin005d.ui.pages.ContactoScreen
import com.example.proyectologin005d.ui.pages.IndexScreen
import com.example.proyectologin005d.ui.pages.Nosotros
import com.example.proyectologin005d.ui.pages.PerfilScreen
import com.example.proyectologin005d.ui.pages.ProductosScreen
import com.example.proyectologin005d.view.ProductoFormScreen

sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Index     : Screen("index",       "Inicio",   { Icon(Icons.Default.Home,         null) })
    object Nosotros  : Screen("nosotros",    "Nosotros", { Icon(Icons.Default.Info,         null) })
    object Contacto  : Screen("contactanos", "Contacto", { Icon(Icons.Default.Mail,         null) })
    object Productos : Screen("productos",   "Productos",{ Icon(Icons.Default.Star,         null) })
    object Carrito   : Screen("carrito",     "Carrito",  { Icon(Icons.Default.ShoppingCart, null) })
    object Perfil    : Screen("perfil",      "Perfil",   { Icon(Icons.Default.Person,       null) })
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authRepo = remember { AuthRepository(context) }

    val screens = listOf(
        Screen.Index, Screen.Nosotros, Screen.Contacto,
        Screen.Productos, Screen.Carrito, Screen.Perfil
    )
    val screenRoutes = screens.map { it.route }.toSet()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = backStackEntry?.destination?.route in screenRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar {
                    val currentDestination = backStackEntry?.destination
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
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Auth
            composable("login") { LoginScreen(navController) }
            composable("register") {
                RegisterScreen(navController = navController, repo = authRepo)
            }

            // Contenido con BottomBar
            composable("index")       { IndexScreen(navController) }
            composable("nosotros")    { Nosotros() }
            composable("contactanos") { ContactoScreen() }
            composable("productos")   { ProductosScreen() }
            composable("carrito")     { CarritoScreen() }
            composable("perfil") { PerfilScreen(navController) }

            // Pantalla con argumentos
            composable(
                route = "ProductoFormScreen/{nombre}/{precio}",
                arguments = listOf(
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("precio") { type = NavType.StringType }
                )
            ) { backStack ->
                val nombre = Uri.encode(backStack.arguments?.getString("nombre") ?: "")
                val precio = backStack.arguments?.getString("precio") ?: ""
                ProductoFormScreen(nombre = nombre, precio = precio, navController = navController)
            }
        }
    }
}
