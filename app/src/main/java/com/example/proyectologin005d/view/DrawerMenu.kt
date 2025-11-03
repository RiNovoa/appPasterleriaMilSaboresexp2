package com.example.proyectologin005d.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class) // ← silencia el warning del TopAppBar
@Composable
fun DrawerMenu(
    username: String,
    navController: NavController // ← renombrado para evitar warning “unused”
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Pastelería • Hola $username") }
            )
        }
    ) { inner ->
        LazyColumn(
    username:String,
    navController: NavController
) { // inicio
    Column(modifier = Modifier.fillMaxSize())
    { // inicio columna
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) // fin box

        { // inicio contenido
            Text(
                text="Categorias user: $username" ,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )// fin texto
        }// fin contenido

        //  Items


        //LazyColumn: Crea una lista de elementos que se pueden desplazar verticalmente.
        // Solo los elementos que están visibles en la pantalla se crean y se muestran,
        // lo que mejora el rendimiento, especialmente para listas grandes.

        LazyColumn( modifier = Modifier.weight(1f)) {
            item{ // inicio item 1
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Hamburgesa Clasica")},
                    selected =false,
                    onClick = {
                        val nombre = Uri.encode("Hamburgesa Clasica")
                        val precio ="5000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    }, // fin onclick
                    icon = {Icon(Icons.Default.Fastfood ,  contentDescription ="Clasica" )}

                ) // fin DrawerItem
            } // fin item 1


            item{ // inicio item 2
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Hamburgesa BBQ")},
                    selected =false,
                    onClick = { /*  accion */
                    }, // fin onclick
                    icon = {Icon(Icons.Default.LunchDining ,  contentDescription ="BBQ" )}

                ) // fin DrawerItem
            } // fin item 2


            item{ // inicio item 3
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Hamburgesa Veggie")},
                    selected =false,
                    onClick = { /*  accion */
                    }, // fin onclick
                    icon = {Icon(Icons.Default.Grass ,  contentDescription ="Veggie" )}

                ) // fin DrawerItem
            } // fin item 3

            item{ // inicio item 4
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Hamburgesa Picante")},
                    selected =false,
                    onClick = { /*  accion */
                    }, // fin onclick
                    icon = {Icon(Icons.Default.LocalFireDepartment ,  contentDescription ="Picante" )}

                ) // fin DrawerItem
            } // fin item 4

            item{ // inicio item 5
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Hamburgesa Doble")},
                    selected =false,
                    onClick = { /*  accion */
                    }, // fin onclick
                    icon = {Icon(Icons.Default.Star ,  contentDescription ="Doble" )}

                ) // fin DrawerItem
            } // fin item 5

            item {
                NavigationDrawerItem(
                    label = { Text("Contáctanos") },
                    selected = false,
                    onClick = { navController.navigate("contactanos") },
                    icon = { Icon(Icons.Default.Email, contentDescription = "Contáctanos") }
                )
            }


        } // fin Lazy

//  Footer del drawer
        Text(
            text ="@ 2025 Burger App",
            style = MaterialTheme.typography.bodySmall,

            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Text("Inicio", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                ListItem(
                    headlineContent = { Text("Ir al inicio") },
                    leadingContent = { Icon(Icons.Filled.Home, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
            }

            item {
                Text("Productos", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                ListItem(
                    headlineContent = { Text("Tortas y Pasteles") },
                    leadingContent = { Icon(Icons.Filled.Cake, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                ListItem(
                    headlineContent = { Text("Cafetería") },
                    leadingContent = { Icon(Icons.Filled.LocalCafe, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
            }

            item {
                Text("Pedidos", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                ListItem(
                    headlineContent = { Text("Mi carrito") },
                    leadingContent = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
