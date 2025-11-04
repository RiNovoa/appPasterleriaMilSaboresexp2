package com.example.proyectologin005d.ui.pages

import android.net.Uri
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private fun assetUrl(imageName: String): String {
    return "file:///android_asset/img/${Uri.encode(imageName)}"
}

@Composable
fun Nosotros() {
    val scroll = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        expanded = true
    }

    val padding by animateDpAsState(
        targetValue = if (expanded) 16.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(padding)
            .alpha(if (expanded) 1f else 0f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nosotros",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Conoce más sobre nuestra historia, misión y visión como Pastelería 1000 Sabores.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        AsyncImage(
            model = assetUrl("tienda.png"),
            contentDescription = "Nuestra tienda",
            modifier = Modifier.fillMaxWidth().height(220.dp).padding(vertical = 8.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Nuestra Historia",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = """
Pastelería 1000 Sabores celebra su 50° aniversario como un referente en la repostería chilena. 
Famosa por su participación en un récord Guinness en 1995, cuando colaboró en la creación de la torta más grande del mundo. 
Hoy continuamos innovando para mantener viva nuestra tradición dulce y artesanal.
""".trimIndent(),
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        AsyncImage(
            model = assetUrl("local.jpg"),
            contentDescription = "Nuestro local",
            modifier = Modifier.fillMaxWidth().height(220.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Nuestra Misión",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = """
Ofrecer una experiencia dulce y memorable a nuestros clientes, elaborando productos de repostería de alta calidad para todas las ocasiones. 
Celebramos nuestras raíces y fomentamos la creatividad para que cada torta y postre sea una obra única.
""".trimIndent(),
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        AsyncImage(
            model = assetUrl("vitrina.png"),
            contentDescription = "Vitrina de pasteles",
            modifier = Modifier.fillMaxWidth().height(220.dp).padding(vertical = 8.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Nuestra Visión",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = """
Convertirnos en la tienda online líder en repostería en Chile, reconocida por nuestra innovación, calidad y compromiso con la comunidad. 
Buscamos inspirar a nuevos talentos en el mundo de la gastronomía y seguir siendo un símbolo de sabor y tradición.
""".trimIndent(),
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(bottom = 32.dp, top = 8.dp)
        )
    }
}
