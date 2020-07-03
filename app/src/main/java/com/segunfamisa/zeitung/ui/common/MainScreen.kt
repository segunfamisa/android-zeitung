package com.segunfamisa.zeitung.ui.common

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.layout.Column
import androidx.ui.layout.Row


@Composable
fun MainContent(
    navBarState : NavBarState,
    onNavItemSelected: (NavItem) -> Boolean
) {
    Column {
        Row(modifier = Modifier.weight(1f)) {
            // TODO body corresponding to each selection goes here
        }
        Row {
            BottomNavBar(
                state = navBarState,
                onItemSelected = onNavItemSelected
            )
        }
    }
}
