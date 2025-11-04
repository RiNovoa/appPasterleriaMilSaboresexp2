package com.example.proyectologin005d.ui.pages

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectologin005d.data.Pastel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException

@Composable
fun IndexScreen(navController: NavController) {
    val context = LocalContext.current
    val pasteles = remember { loadPasteles(context) }
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .background(Color(0xFFFFF8F2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "file:///android_asset/img/logo.png"),
            contentDescription = "Logo Pastelería 1000 Sabores",
            modifier = Modifier.height(120.dp).padding(8.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Pastelería 1000 Sabores",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF4A3C2A),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Image(
            painter = rememberAsyncImagePainter(model = "file:///android_asset/img/local.jpg"),
            contentDescription = "Fachada de la tienda",
            modifier = Modifier.fillMaxWidth().height(200.dp).padding(vertical = 8.dp).clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Bienvenidos al dulce mundo de 1000 Sabores. " +
                    "Aquí cada pastel es una historia, cada sabor un recuerdo y cada visita una sonrisa.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF5E5E5E),
            modifier = Modifier.padding(vertical = 12.dp)
        )

        HorizontalDivider(
            color = Color(0xFFE0E0E0),
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Text(
            text = "Destacados de la semana",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A3C2A),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        pasteles.take(4).forEach { pastel ->
            ProductCard(
                image = pastel.imagen,
                nombre = pastel.nombre,
                precio = "$${pastel.precio}",
                descripcion = pastel.descripcion
            )
        }

        HorizontalDivider(
            color = Color(0xFFE0E0E0),
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Text(
            text = "Hecho con amor desde 1975 💖",
            fontSize = 14.sp,
            color = Color(0xFF8D6E63),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun ProductCard(image: String, nombre: String, precio: String, descripcion: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "file:///android_asset/img/" + Uri.encode(image)),
                contentDescription = nombre,
                modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A3C2A),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = precio,
                fontSize = 16.sp,
                color = Color(0xFFD81B60),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = Color(0xFF5E5E5E),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

fun loadPasteles(context: Context): List<Pastel> {
    val jsonString: String
    try {
        jsonString = context.assets.open("database/Pasteles.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return emptyList()
    }
    return Json { ignoreUnknownKeys = true }.decodeFromString(jsonString)
}