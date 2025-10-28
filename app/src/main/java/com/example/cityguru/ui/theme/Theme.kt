package com.example.cityguru.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val DarkColorScheme = darkColorScheme(
    surface = White,
    primary = Black,
    background = Purple,
    tertiary = Gray,
    onSecondary = GrayDark,
    onSurface = AccentBrand,
    onPrimary = BackgroundPressed,
    onTertiary = Secondary
)

private val LightColorScheme = lightColorScheme(
    surface = White,
    primary = Black,
    background = Purple,
    tertiary = Gray,
    onSecondary = GrayDark,
    onSurface = AccentBrand,
    onPrimary = BackgroundPressed,
    onTertiary = Secondary
)

@Composable
fun CityGuruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}