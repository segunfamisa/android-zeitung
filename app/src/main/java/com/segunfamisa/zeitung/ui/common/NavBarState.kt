package com.segunfamisa.zeitung.ui.common

import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import com.segunfamisa.zeitung.R

sealed class NavItem(val index: Int, val title: String, val icon: VectorAsset) {
    object News : NavItem(
        0,
        stringResource(id = R.string.menu_news),
        vectorResource(id = R.drawable.ic_nav_menu_newspaper)
    )

    object Saved : NavItem(
        1,
        stringResource(id = R.string.menu_bookmarks),
        vectorResource(id = R.drawable.ic_nav_menu_bookmark)
    )
}

class NavBarState(initialSelection: NavItem) {
    var currentSelection by mutableStateOf<NavItem>(initialSelection)
        private set

    fun updateSelection(newSelection: NavItem) {
        currentSelection = newSelection
    }
}
