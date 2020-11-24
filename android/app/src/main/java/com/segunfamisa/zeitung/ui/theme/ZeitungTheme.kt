package com.segunfamisa.zeitung.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val lightThemeColors = lightColors(
    primary = Color(0xFFFFFFFF),
    primaryVariant = Color(0xFFCFCFCF),
    secondary = Color(0xFF4EBAAA),
    secondaryVariant = Color(0xFF4EBAAA),
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

private val darkThemeColors = darkColors(
    primary = Color(0xFF333333),
    primaryVariant = Color(0xFF1b1b1b),
    secondary = Color(0xFF3EB3A1),
    background = Color(0xFF1b1b1b),
    surface = Color(0xFF1b1b1b),
    error = Color(0xFF770217),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White
)

@Composable
fun colors(darkTheme: Boolean = isSystemInDarkTheme()) =
    if (darkTheme) darkThemeColors else lightThemeColors

@Composable
fun secondary() = colors().secondary

@Composable
fun typography() = zeitungTypography()

@Composable
fun shapes() = MaterialTheme.shapes

@Composable
fun ZeitungTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    children: @Composable() () -> Unit
) {
    MaterialTheme(
        colors = colors(darkTheme),
        typography = typography(),
        shapes = shapes(),
        content = children
    )
}