package com.segunfamisa.zeitung.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

private val White = Color(0xffffffff)

/**
 * Generated on https://m3.material.io/theme-builder#/custom
 */
private val lightColorScheme = lightColorScheme(
    primary = Color(0xFF006B5A),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF7AF8DA),
    onPrimaryContainer = Color(0xFF00201A),

    secondary = Color(0xFF4B635C),
    onSecondary = White,
    secondaryContainer = Color(0xFFCDE8DF),
    onSecondaryContainer = Color(0xFF07201A),

    tertiary = Color(0xFF426277),
    onTertiary = White,
    tertiaryContainer = Color(0xFFC7E7FF),
    onTertiaryContainer = Color(0xFF001E2E),

    background = Color(0xFFFAFDFA),
    onBackground = Color(0xFF191C1B),

    surface = Color(0xFFFAFDFA),
    onSurface = Color(0xFF191C1B),

    surfaceVariant = Color(0xFFDBE5E0),
    onSurfaceVariant = Color(0xFF3F4945),

    outline = Color(0xFF6F7975),
)

private val darkColorScheme = lightColorScheme(
    primary = Color(0xFF5BDBBF),
    onPrimary = Color(0xFF00382E),
    primaryContainer = Color(0xFF005143),
    onPrimaryContainer = Color(0xFF7AF8DA),

    secondary = Color(0xFFB1CCC3),
    onSecondary = Color(0xFF1D352F),
    secondaryContainer = Color(0xFF334B45),
    onSecondaryContainer = Color(0xFFCDE8DF),

    tertiary = Color(0xFFAACBE3),
    onTertiary = Color(0xFF103447),
    tertiaryContainer = Color(0xFF2A4A5F),
    onTertiaryContainer = Color(0xFFC7E7FF),

    background = Color(0xFF191C1B),
    onBackground = Color(0xFFE1E3E0),

    surface = Color(0xFF191C1B),
    onSurface = Color(0xFFE1E3E0),

    surfaceVariant = Color(0xFF3F4945),
    onSurfaceVariant = Color(0xFFBFC9C4),

    outline = Color(0xFF89938F),
)

@Composable
fun colorScheme(darkTheme: Boolean = isSystemInDarkTheme()): ColorScheme {
    return if (darkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }
}

// source: https://github.com/android/compose-samples/blob/main/Owl/app/src/main/java/com/example/owl/ui/theme/Color.kt
/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun ColorScheme.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}