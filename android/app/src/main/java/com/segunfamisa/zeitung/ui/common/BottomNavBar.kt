package com.segunfamisa.zeitung.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.common.theme.preview.ThemedUiPreview

sealed class NavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    object News : NavItem(R.string.menu_news, R.drawable.ic_nav_menu_home, Routes.News)
    object Sources : NavItem(R.string.menu_sources, R.drawable.ic_nav_menu_explore, Routes.Sources)
    object Bookmarks :
        NavItem(R.string.menu_bookmarks, R.drawable.ic_nav_menu_bookmark, Routes.Bookmarks)
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    items: List<NavItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        items.forEach { navItem ->
            val isSelected = currentDestination?.hierarchy?.any { it.route?.equals(navItem.route) == true } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = stringResource(id = navItem.title),
                    )
                },
                label = { Text(stringResource(id = navItem.title)) },
                selected = isSelected,
                onClick = {
                    navController.navigate(navItem.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
@ThemedUiPreview
private fun BottomNavPreview() {
    ZeitungTheme {
        val items = listOf(NavItem.News, NavItem.Sources, NavItem.Bookmarks)
        BottomNavBar(
            navController = rememberNavController(ComposeNavigator()),
            items = items
        )
    }
}
