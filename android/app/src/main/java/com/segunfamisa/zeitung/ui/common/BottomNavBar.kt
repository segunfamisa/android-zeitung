package com.segunfamisa.zeitung.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.common.theme.colors

sealed class NavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    object News : NavItem(R.string.menu_news, R.drawable.ic_nav_menu_newspaper, Routes.News)
    object Explore : NavItem(R.string.menu_browse, R.drawable.ic_nav_menu_browse, Routes.Explore)
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
    BottomNavigation {
        items.forEach { navItem ->
            val isSelected = currentDestination?.hierarchy?.any { it.route?.equals(navItem.route) == true } == true
            BottomNavigationItem(
                icon = { BottomNavIcon(navItem) },
                label = { BottomNavText(navItem, isSelected) },
                selected = isSelected,
                alwaysShowLabel = true,
                selectedContentColor = colors().secondary,
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
private fun BottomNavIcon(
    navItem: NavItem
) {
    Icon(
        painter = painterResource(id = navItem.icon),
        contentDescription = null,
        modifier = Modifier,
    )
}

@Composable
private fun BottomNavText(
    navItem: NavItem,
    isSelected: Boolean
) {
    Text(
        text = stringResource(id = navItem.title),
        fontWeight = if (isSelected) FontWeight.Bold else null
    )
}
