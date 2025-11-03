package com.example.proyectologin005d.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

// Fallbacks (sin dependencias de fuentes externas)
private val Lato = FontFamily.SansSerif
private val Pacifico = FontFamily.Cursive

val AppTypography = Typography(
    displayLarge  = TextStyle(fontFamily = Pacifico, fontSize = 40.sp),
    displayMedium = TextStyle(fontFamily = Pacifico, fontSize = 34.sp),
    displaySmall  = TextStyle(fontFamily = Pacifico, fontSize = 28.sp),

    headlineLarge  = TextStyle(fontFamily = Pacifico, fontSize = 26.sp),
    headlineMedium = TextStyle(fontFamily = Pacifico, fontSize = 22.sp),
    headlineSmall  = TextStyle(fontFamily = Pacifico, fontSize = 20.sp),

    titleLarge  = TextStyle(fontFamily = Lato, fontSize = 20.sp),
    titleMedium = TextStyle(fontFamily = Lato, fontSize = 18.sp),
    titleSmall  = TextStyle(fontFamily = Lato, fontSize = 16.sp),

    bodyLarge  = TextStyle(fontFamily = Lato, fontSize = 16.sp, color = TextoMarron),
    bodyMedium = TextStyle(fontFamily = Lato, fontSize = 14.sp, color = TextoMarron),
    bodySmall  = TextStyle(fontFamily = Lato, fontSize = 12.sp, color = TextoSecund),

    labelLarge  = TextStyle(fontFamily = Lato, fontSize = 14.sp),
    labelMedium = TextStyle(fontFamily = Lato, fontSize = 12.sp),
    labelSmall  = TextStyle(fontFamily = Lato, fontSize = 11.sp)
)
