package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = IslamicGreenDark,
    onPrimary = Color(0xFF003822),
    primaryContainer = IslamicGreenDarkContainer,
    onPrimaryContainer = Color(0xFFA6F5C9),
    secondary = IslamicGoldDark,
    onSecondary = Color(0xFF3F2E00),
    secondaryContainer = IslamicGoldDarkContainer,
    onSecondaryContainer = Color(0xFFFFE08B),
    background = NightBackground,
    onBackground = NightOnSurface,
    surface = NightSurface,
    onSurface = NightOnSurface,
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = NightOnSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = IslamicGreenLight,
    onPrimary = Color.White,
    primaryContainer = IslamicGreenLightContainer,
    onPrimaryContainer = Color(0xFF002113),
    secondary = IslamicGoldLight,
    onSecondary = Color.White,
    secondaryContainer = IslamicGoldLightContainer,
    onSecondaryContainer = Color(0xFF261900),
    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant
)

@Composable
fun DailyQuranTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
