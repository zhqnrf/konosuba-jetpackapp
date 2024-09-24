package com.example.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val TwitterColors = object {
    val Blue = Color(0xFF1DA1F2)
    val Black = Color(0xFF14171A) // Hitam
    val DarkGray = Color(0xFF657786) // Abu-abu Gelap
    val LightGray = Color(0xFFAAB8C2) // Abu-abu Terang
    val White = Color(0xFFFFFFFF) // Putih
}

private val DarkColorPalette = darkColors(
    primary = TwitterColors.Blue,
    primaryVariant = TwitterColors.DarkGray,
    secondary = TwitterColors.LightGray
)

private val LightColorPalette = lightColors(
    primary = TwitterColors.Blue,
    primaryVariant = TwitterColors.DarkGray,
    secondary = TwitterColors.LightGray,

    // Warna default lainnya
    background = TwitterColors.White,
    surface = TwitterColors.White,
    onPrimary = TwitterColors.White,
    onSecondary = TwitterColors.Black,
    onBackground = TwitterColors.Black,
    onSurface = TwitterColors.Black,
)

@Composable
fun SubmissionComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
