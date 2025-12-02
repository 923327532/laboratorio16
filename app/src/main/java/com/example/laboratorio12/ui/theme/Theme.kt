package com.example.laboratorio12.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MisCursosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}

// Define tu esquema de colores claro/oscuro
val LightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primary,
    onPrimaryContainer = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary,
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    onBackground = md_theme_light_onBackground,
    onSurface = md_theme_light_onSurface
)

val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    // Agrega valores si usas modo oscuro
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    background = Color.Black,
    surface = Color.DarkGray
)