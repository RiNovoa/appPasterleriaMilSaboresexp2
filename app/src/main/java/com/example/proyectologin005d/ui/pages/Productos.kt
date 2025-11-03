@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.proyectologin005d.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.proyectologin005d.data.local.CatalogoProductoJson
import com.example.proyectologin005d.data.local.JsonReader

@Composable
fun ProductosScreen() {
    val context = LocalContext.current
    var productos by remember { mutableStateOf<List<CatalogoProductoJson>>(emptyList()) }

    // Cargar el JSON una sola vez al entrar a la pantalla
    LaunchedEffect(Unit) {
        productos = JsonReader.cargarCatalogo(context) // lee app > assets > Pasteles.json
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Catálogo Mil Sabores") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(productos) { p ->
                Card(Modifier.padding(bottom = 8.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text(p.nombre, style = MaterialTheme.typography.titleMedium)
                        Text("Precio: CLP ${p.precio}")
                        Text("Categoría: ${p.categoria ?: "-"}")
                    }
                }
            }
        }
    }
}
