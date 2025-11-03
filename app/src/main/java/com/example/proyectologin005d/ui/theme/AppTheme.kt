package com.example.proyectologin005d.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Mapeo de la paleta a Material3
private val LightColors = lightColorScheme(
    primary = Chocolate,      // Botones principales (chocolate)
    onPrimary = Color.White,
    secondary = RosaSuave,    // Acentos (rosa)
    onSecondary = TextoMarron,
    background = CremaPastel, // Fondo
    onBackground = TextoMarron,
    surface = Color.White,
    onSurface = TextoMarron
)

private val DarkColors = darkColorScheme(
    // Si quisieras modo oscuro custom, lo definimos después.
)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography, // desde Type.kt
        content = content
    )
}
