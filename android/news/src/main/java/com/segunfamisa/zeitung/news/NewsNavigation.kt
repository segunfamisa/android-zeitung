package com.segunfamisa.zeitung.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
        NewsRoute(route = route, vmFactory = vmFactory)
    }
}

/**
 * Renders the NewsContent
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoilApi::class)
@Composable
private fun NewsRoute(route: String, vmFactory: ViewModelProvider.Factory) {
    val viewModel = viewModel<NewsViewModel>(factory = vmFactory)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(route) {
        viewModel.fetchHeadlines()
    }

    NewsContent(uiState = uiState)
}
