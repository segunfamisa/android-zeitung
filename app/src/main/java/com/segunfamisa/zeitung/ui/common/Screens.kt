package com.segunfamisa.zeitung.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ScreenState(default: Screen) {
    var currentScreen by mutableStateOf(default)
}

sealed class Screen {
    object News : Screen()
    object Browse : Screen()
    object Saved : Screen()
}
