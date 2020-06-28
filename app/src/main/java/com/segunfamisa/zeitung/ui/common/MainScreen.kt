package com.segunfamisa.zeitung.ui.common

import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.contentColor
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.material.BottomNavigation
import androidx.ui.material.BottomNavigationItem
import com.segunfamisa.zeitung.ui.theme.secondary

@Composable
fun MainContent(
    items: List<NavItem>,
    state: NavBarState,
    onItemSelected: (NavItem) -> Boolean
) {
    Column {
        Row(modifier = Modifier.weight(1f)) {
            // TODO body corresponding to each selection goes here
        }
        Row {
            BottomNavBar(
                items = items,
                state = state,
                onItemSelected = onItemSelected
            )
        }
    }
}

@Composable
private fun BottomNavBar(
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
                    val selected = onItemSelected(navItem)
                    if (selected) {
                        state.updateSelection(navItem)
                    }
                    // we no longer need to set initial item since the callback is triggered
                    shouldSelectInitialNavItem = false
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
        asset = navItem.icon,
        modifier = Modifier,
        tint = if (isSelected) secondary() else contentColor()
    )
}

@Composable
private fun BottomNavText(
    navItem: NavItem,
    isSelected: Boolean
) {
    Text(navItem.title, color = if (isSelected) secondary() else Color.Unset)
}
