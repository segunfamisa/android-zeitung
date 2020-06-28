package com.segunfamisa.zeitung.ui.common

import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue
import androidx.ui.graphics.vector.VectorAsset

class NavItem(val index: Int, val title: String, val icon: VectorAsset)

class NavBarState(initialSelection: NavItem) {
    var currentSelection by mutableStateOf<NavItem>(initialSelection)
        private set

    fun updateSelection(newSelection: NavItem) {
        currentSelection = newSelection
    }
}
