package com.segunfamisa.zeitung.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.bookmarks.bookmarksGraph
import com.segunfamisa.zeitung.common.AppState
import com.segunfamisa.zeitung.news.newsNavGraph
import com.segunfamisa.zeitung.sources.sourcesNavGraph


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun MainApp(appState: AppState, vmFactory: ViewModelProvider.Factory) {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryFlow.collectAsState(navController.currentBackStackEntry)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (backStack?.destination?.route) {
                            Routes.Sources -> stringResource(R.string.app_bar_sources)
                            Routes.Bookmarks -> stringResource(id = R.string.app_bar_bookmarks)
                            Routes.News -> stringResource(id = R.string.app_bar_news)
                            else -> stringResource(R.string.app_name)
                        }
                    )
                }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (appState.shouldUseBottomBar) {
                BottomNavBar(
                    navController = navController,
                    items = listOf(NavItem.News, NavItem.Bookmarks, NavItem.Sources)
                )
            }
        },
    ) { padding ->
        Row {
            if (appState.shouldUseNavRail) {
                NavRail(
                    navController = navController,
                    items = listOf(NavItem.News, NavItem.Bookmarks, NavItem.Sources),
                    modifier = Modifier.safeDrawingPadding()
                )
            }
            NavHost(
                navController = navController,
                startDestination = Routes.News,
                modifier = Modifier
                    .padding(padding)
                    .consumedWindowInsets(padding)
            ) {
                newsNavGraph(route = Routes.News, vmFactory = vmFactory)
                sourcesNavGraph(route = Routes.Sources, vmFactory = vmFactory)
                bookmarksGraph(Routes.Bookmarks, vmFactory = vmFactory)
            }
        }
    }
}
