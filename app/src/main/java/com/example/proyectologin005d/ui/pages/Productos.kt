@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.proyectologin005d.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.proyectologin005d.data.local.CatalogoProductoJson
import com.example.proyectologin005d.data.local.JsonReader

// Convierte la ruta del JSON a una URL válida para /android_asset/
private fun assetUrlFromJsonPath(path: String?): String? {
    if (path.isNullOrBlank()) return null
    // Si ya viene como URL http(s), úsala tal cual
    if (path.startsWith("http://") || path.startsWith("https://")) return path

    // Limpia prefijos típicos del JSON
    val cleaned = path
        .removePrefix("../assets/")
        .removePrefix("./assets/")
        .removePrefix("assets/")
        .removePrefix("/")

    // Asegura que apunte a la carpeta assets/ (por ejemplo "img/archivo.webp")
    return "file:///android_asset/$cleaned".replace(" ", "%20")
}

@Composable
fun ProductosScreen() {
    val context = LocalContext.current
    var productos by remember { mutableStateOf<List<CatalogoProductoJson>>(emptyList()) }

    LaunchedEffect(Unit) {
        productos = JsonReader.cargarCatalogo(context)
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
                Card(Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                    Column(Modifier.padding(12.dp)) {

                        AsyncImage(
                            model = assetUrlFromJsonPath(p.imagen),
                            contentDescription = p.nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.height(8.dp))
                        Text(p.nombre, style = MaterialTheme.typography.titleMedium)
                        Text("Precio: CLP ${p.precio}")
                        Text("Categoría: ${p.categoria ?: "-"}")
                    }
                }
            }
        }
    }
}
