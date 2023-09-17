package com.segunfamisa.zeitung.sources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.sourcesNavGraph(route: String, vmFactory: ViewModelProvider.Factory) {
    composable(route = route) {
        SourcesRoute(vmFactory)
    }
}

@Composable
private fun SourcesRoute(vmFactory: ViewModelProvider.Factory) {
    val viewModel = viewModel<SourcesViewModel>(factory = vmFactory)

    val uiState by viewModel.uiState.collectAsState()
    SourcesContent(
        uiState = uiState,
        onSourceFollowed = { sourceId, followed ->
            viewModel.followSource(sourceId = sourceId, followed = followed)
        }
    )
}
