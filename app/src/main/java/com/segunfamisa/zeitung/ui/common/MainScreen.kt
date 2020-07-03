package com.segunfamisa.zeitung.ui.common

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import com.segunfamisa.zeitung.di.AppContainer
import com.segunfamisa.zeitung.di.NewsContainer
import com.segunfamisa.zeitung.ui.news.NewsContent


@Composable
fun MainContent(
    navBarState : NavBarState,
    onNavItemSelected: (NavItem) -> Boolean,
    appContainer: AppContainer
) {
    Column {
        Row(modifier = Modifier.weight(1f)) {
            MainContentBody(
                appContainer = appContainer,
                state = navBarState
            )
        }
        Row {
            BottomNavBar(
                state = navBarState,
                onItemSelected = onNavItemSelected
            )
        }
    }
}

@Composable
fun MainContentBody(
    appContainer: AppContainer,
    state: NavBarState
) {
    when (state.currentSelection) {
        is NavItem.News -> renderNewsContent(appContainer.newsContainer())
        else -> Unit
    }
}

@Composable
private fun renderNewsContent(newsContainer: NewsContainer) {
    NewsContent(newsViewModel = newsContainer.newsViewModel.value)
}
