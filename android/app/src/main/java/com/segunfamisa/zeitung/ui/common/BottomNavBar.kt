package com.segunfamisa.zeitung.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.theme.secondary

sealed class NavItem(val index: Int, @StringRes val title: Int, @DrawableRes val icon: Int) {
    object News : NavItem(0, R.string.menu_news, R.drawable.ic_nav_menu_newspaper)
    object Explore : NavItem(1, R.string.menu_browse, R.drawable.ic_nav_menu_browse)
    object Bookmarks : NavItem(2, R.string.menu_bookmarks, R.drawable.ic_nav_menu_bookmark)
}

class NavBarState(val navItems: List<NavItem>, defaultSelectedIndex: Int = 0) {
    var currentSelection by mutableStateOf(navItems[defaultSelectedIndex])
        private set

    fun update(newSelection: NavItem) {
        currentSelection = newSelection
    }
}

@Composable
fun BottomNavBar(
    state: NavBarState,
    onItemSelected: (NavItem) -> Boolean
) {
    var shouldSelectInitialNavItem: Boolean = remember { true }
    BottomNavigation {
        state.navItems.forEach { navItem ->
            val isSelected = navItem.index == state.currentSelection.index
            BottomNavigationItem(
                icon = { BottomNavIcon(navItem, isSelected) },
                label = { BottomNavText(navItem, isSelected) },
                selected = isSelected,
                alwaysShowLabels = true,
                onClick = {
                    // We want to avoid reselection. In the future, I may provide item reselection
                    // callbacks. But for now, no need.
                    if (!isSelected) {
                        val selected = onItemSelected(navItem)
                        if (selected) {
                            state.update(navItem)
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
        tint = if (isSelected) secondary() else AmbientContentColor.current
    )
}

@Composable
private fun BottomNavText(
    navItem: NavItem,
    isSelected: Boolean
) {
    Text(
        text = stringResource(id = navItem.title),
        color = if (isSelected) secondary() else Color.Unspecified,
        fontWeight = if (isSelected) FontWeight.Bold else null
    )
}
