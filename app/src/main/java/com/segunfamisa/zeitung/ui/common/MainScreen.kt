package com.segunfamisa.zeitung.ui.common

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.layout.Column
import androidx.ui.layout.Row


@Composable
fun MainContent(
    onItemSelected: (NavItem) -> Boolean
) {
    Column {
        Row(modifier = Modifier.weight(1f)) {
            // TODO body corresponding to each selection goes here
        }
        Row {
            val navItems: List<NavItem> = listOf(NavItem.News, NavItem.Bookmarks)
            val navBarState = NavBarState(navItems.first())
            BottomNavBar(
                items = navItems,
                state = navBarState,
                onItemSelected = onItemSelected
            )
        }
    }
}
