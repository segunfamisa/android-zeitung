package com.segunfamisa.zeitung.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun shapes() = MaterialTheme.shapes

@Composable
fun ZeitungTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    children: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme(darkTheme),
        shapes = shapes(),
        typography = typography(),
        content = children
    )
}