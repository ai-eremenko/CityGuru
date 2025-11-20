package com.example.cityguru.uikit.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    surface = White,
    primary = Black,
    background = Purple,
    tertiary = Gray,
    onSecondary = GrayDark,
    onSurface = AccentBrand,
    onPrimary = BackgroundPressed,
    onTertiary = Secondary,
    surfaceTint = PrimaryBackground
)

@Composable
fun CityGuruTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}