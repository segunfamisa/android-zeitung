package com.segunfamisa.zeitung.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi


fun NavGraphBuilder.newsNavGraph(route: String, vmFactory: ViewModelProvider.Factory) {
    composable(route = route) {
        NewsRoute(vmFactory = vmFactory)
    }
}

/**
 * Renders the NewsContent
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoilApi::class)
@Composable
private fun NewsRoute(vmFactory: ViewModelProvider.Factory) {
    val viewModel = viewModel<NewsViewModel>(factory = vmFactory)
    val uiState by viewModel.uiState.collectAsState()

    NewsContent(
        uiState = uiState,
        onNewsItemSaved = { url, saved ->
            viewModel.saveNewsItem(url = url, saved = saved)
        }
    )
}
