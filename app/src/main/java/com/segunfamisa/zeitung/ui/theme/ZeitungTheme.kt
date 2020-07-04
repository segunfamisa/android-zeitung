package com.segunfamisa.zeitung.ui.theme

import androidx.compose.Composable
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import androidx.ui.material.lightColorPalette

private val colors = lightColorPalette(
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

@Composable
fun secondary() = colors.secondary

@Composable
fun typography() = MaterialTheme.typography

@Composable
fun shapes() = MaterialTheme.shapes

@Composable
fun ZeitungTheme(children: @Composable() () -> Unit) {
    MaterialTheme(
        colors = colors,
        typography = typography(),
        shapes = shapes(),
        content = children
    )
}