package com.segunfamisa.zeitung.common.theme.preview

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.segunfamisa.zeitung.common.theme.ZeitungTheme

@Composable
fun ThemedPreview(
    darkTheme: Boolean = false,
    children: @Composable() () -> Unit
) {
    ZeitungTheme(darkTheme = darkTheme) {
        Surface {
            children()
        }
    }
}
