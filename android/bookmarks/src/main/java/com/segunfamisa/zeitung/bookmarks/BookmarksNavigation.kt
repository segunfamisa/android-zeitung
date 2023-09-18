package com.segunfamisa.zeitung.bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.bookmarksGraph(route: String, vmFactory: ViewModelProvider.Factory) {
    composable(route = route) {
        BookmarksRoute(vmFactory = vmFactory)
    }
}

@Composable
private fun BookmarksRoute(vmFactory: ViewModelProvider.Factory) {
    val viewModel = viewModel<BookmarksViewModel>(factory = vmFactory)
    val uiState by viewModel.uiState.collectAsState()

    BookmarksContent(uiState = uiState, onBookmarkRemoved = { url ->
        viewModel.removeArticle(url)
    })
}
