package com.segunfamisa.zeitung.common.theme.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.segunfamisa.zeitung.common.theme.ZeitungTheme

@Composable
fun ThemedPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    children: @Composable() () -> Unit
) {
    ZeitungTheme(darkTheme = darkTheme) {
        Surface {
            children()
        }
    }
}
