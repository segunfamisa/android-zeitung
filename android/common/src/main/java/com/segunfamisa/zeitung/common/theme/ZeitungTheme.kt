package com.segunfamisa.zeitung.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

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