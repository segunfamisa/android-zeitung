package com.segunfamisa.zeitung.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.contentColor
import androidx.ui.graphics.Color
import androidx.ui.material.BottomNavigation
import androidx.ui.material.BottomNavigationItem
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.text.font.FontWeight
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.ui.theme.secondary

sealed class NavItem(val index: Int, @StringRes val title: Int, @DrawableRes val icon: Int) {
    object News : NavItem(0, R.string.menu_news, R.drawable.ic_nav_menu_newspaper)
    object Bookmarks : NavItem(1, R.string.menu_bookmarks, R.drawable.ic_nav_menu_bookmark)
}

class NavBarState(initialSelection: NavItem) {
    var currentSelection by mutableStateOf<NavItem>(initialSelection)
        private set

    fun updateSelection(newSelection: NavItem) {
        currentSelection = newSelection
    }
}

@Composable
fun BottomNavBar(
    items: List<NavItem>,
    state: NavBarState,
    onItemSelected: (NavItem) -> Boolean
) {
    var shouldSelectInitialNavItem: Boolean = remember { true }
    BottomNavigation {
        items.forEach { navItem ->
            val isSelected = navItem.index == state.currentSelection.index
            BottomNavigationItem(
                icon = { BottomNavIcon(navItem, isSelected) },
                text = { BottomNavText(navItem, isSelected) },
                selected = isSelected,
                onSelected = {
                    // We want to avoid reselection. In the future, I may provide item reselection
                    // callbacks. But for now, no need.
                    if (!isSelected) {
                        val selected = onItemSelected(navItem)
                        if (selected) {
                            state.updateSelection(navItem)
                        }
                        // we no longer need to set initial item since the callback is triggered
                        shouldSelectInitialNavItem = false
                    }
                }
            )

            // automatically trigger "selection" for default selection
            // if we haven't done so already
            if (shouldSelectInitialNavItem && isSelected) {
                onItemSelected(navItem)
            }
        }
    }
}

@Composable
private fun BottomNavIcon(
    navItem: NavItem,
    isSelected: Boolean
) {
    Icon(
        asset = vectorResource(id = navItem.icon),
        modifier = Modifier,
        tint = if (isSelected) secondary() else contentColor()
    )
}

@Composable
private fun BottomNavText(
    navItem: NavItem,
    isSelected: Boolean
) {
    Text(
        text = stringResource(id = navItem.title),
        color = if (isSelected) secondary() else Color.Unset,
        fontWeight = if (isSelected) FontWeight.Bold else null
    )
}
