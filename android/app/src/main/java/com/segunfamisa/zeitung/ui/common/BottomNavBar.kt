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
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
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
    val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
    BottomNavigation {
        items.forEach { navItem ->
            val isSelected = currentRoute == navItem.route
            BottomNavigationItem(
                icon = { BottomNavIcon(navItem) },
                label = { BottomNavText(navItem, isSelected) },
                selected = isSelected,
                alwaysShowLabels = true,
                selectedContentColor = colors().secondary,
                onClick = {
                    if (currentRoute != navItem.route) {
                        navController.navigate(route = navItem.route)
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
