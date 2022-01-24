package com.segunfamisa.zeitung.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.common.theme.colors

sealed class NavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    object News : NavItem(R.string.menu_news, R.drawable.ic_nav_menu_home, Routes.News)
    object Explore : NavItem(R.string.menu_browse, R.drawable.ic_nav_menu_explore, Routes.Explore)
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
fun BottomNav(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit
) {
    Surface(elevation = 4.dp) {
        Row(modifier = modifier
            .background(backgroundColor)
            .padding(8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
private fun BottomNavItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    item: NavItem,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.onBackground

    Box(modifier = modifier
        .then(
            Modifier
                .clip(CircleShape)
                .background(backgroundColor)
                .clickable { onClick() }
        )) {
        Row(modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier,
                tint = contentColor
            )

            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = stringResource(id = item.title),
                    color = contentColor
                )
            }
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

@Preview
@Composable
private fun BottomNavItemPreview() {
    ZeitungTheme(darkTheme = false) {
        val isSelected = true
        val navItem = NavItem.News
        BottomNavItem(
            isSelected = isSelected,
            onClick = {

            },
            item = navItem
        )
    }
}

@Preview
@Composable
private fun BottomNavItemPreviewDarkMode() {
    ZeitungTheme(darkTheme = true) {
        val isSelected = true
        val navItem = NavItem.News
        BottomNavItem(
            isSelected = isSelected,
            onClick = {

            },
            item = navItem
        )
    }
}

@Composable
@Preview
private fun BottomNavPreview() {
    ZeitungTheme {
        val items = listOf(NavItem.News, NavItem.Explore, NavItem.Bookmarks)
        BottomNav() {
            items.forEachIndexed { index, navItem ->
                val isSelected = index == 0
                BottomNavItem(
                    isSelected = isSelected,
                    onClick = {

                    },
                    item = navItem
                )
            }
        }
    }
}
