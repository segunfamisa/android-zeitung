package com.segunfamisa.zeitung.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

private val Green300 = Color(0xff4ebaaa)
private val Green700 = Color(0xff027d69)
private val Green900 = Color(0xff00513f)

private val White = Color(0xffffffff)
private val Black = Color(0xff1b1b1b)

private val lightThemeColors = lightColors(
    primary = White,
    primaryVariant = White,
    onPrimary = Color.Black,
    secondary = Green700,
    secondaryVariant = Green900,
    onSecondary = Color.White,
    background = Color.White,
    error = Color(0xFFB00020),
    onError = Color.White
)

private val darkThemeColors = darkColors(
    primary = Green300,
    primaryVariant = Green700,
    onPrimary = Color.Black,
    secondary = Green300,
    onSecondary = Color.Black,
    background = Black,
    onBackground = White,
    error = Color(0xFF770217),
    onError = Color.White
)

@Composable
fun colors(darkTheme: Boolean = isSystemInDarkTheme()) =
    if (darkTheme) darkThemeColors else lightThemeColors

// source: https://github.com/android/compose-samples/blob/main/Owl/app/src/main/java/com/example/owl/ui/theme/Color.kt
/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}