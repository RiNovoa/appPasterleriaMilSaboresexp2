package com.example.proyectologin005d.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectologin005d.ui.login.LoginScreen
import com.example.proyectologin005d.ui.login.RegisterScreen
import com.example.proyectologin005d.ui.pages.*
import com.example.proyectologin005d.viewmodel.CartViewModel

sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Index : Screen("index", "Inicio", { Icon(Icons.Default.Home, null) })
    object Nosotros : Screen("nosotros", "Nosotros", { Icon(Icons.Default.Info, null) })
    object Contacto : Screen("contactanos", "Contacto", { Icon(Icons.Default.Mail, null) })
    object Productos : Screen("productos", "Productos", { Icon(Icons.Default.Cake, null) })
    object Carrito : Screen("carrito", "Carrito", { Icon(Icons.Default.ShoppingCart, null) })
    object Perfil : Screen("perfil", "Perfil", { Icon(Icons.Default.Person, null) })
}

@Composable
fun AppNav(navController: NavHostController = rememberNavController()) {
    val cartViewModel: CartViewModel = viewModel()

    val screens = listOf(
        Screen.Index, Screen.Nosotros, Screen.Contacto,
        Screen.Productos, Screen.Carrito, Screen.Perfil
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val showBottomBar = screens.any { it.route == currentDestination?.route }

            if (showBottomBar) {
                BottomAppBar {
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
            composable("login") { LoginScreen(navController) }
            composable("register") { RegisterScreen(navController) }
            composable("index") { IndexScreen(navController) }
            composable("nosotros") { Nosotros() }
            composable("contactanos") { ContactoScreen() }
            composable("productos") { ProductosScreen(cartViewModel) }
            composable("carrito") { CarritoScreen(cartViewModel, navController) }
            composable("perfil") { PerfilScreen(navController) }
            composable(
                route = "payment/{total}",
                arguments = listOf(navArgument("total") { type = NavType.IntType })
            ) { backStackEntry ->
                val total = backStackEntry.arguments?.getInt("total") ?: 0
                PaymentScreen(navController, total)
            }
        }
    }
}
