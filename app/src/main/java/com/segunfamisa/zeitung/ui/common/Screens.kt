package com.segunfamisa.zeitung.ui.common

import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue

class ScreenState(default: Screen) {
    var currentScreen by mutableStateOf(default)
}

sealed class Screen {
    object News : Screen()
    object Browse : Screen()
    object Saved : Screen()
}
