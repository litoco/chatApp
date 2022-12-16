package com.example.chatapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = NightPrimary,
    secondary = NightPrimaryLight,
    onPrimary = NightText,
    background = NightBackground
)

private val LightColorPalette = lightColorScheme(
    primary = DayPrimary,
    secondary = DayPrimaryLight,
    onPrimary = DayText,
    background = DayBackground
)

@Composable
fun ChatAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        content = content
    )
}