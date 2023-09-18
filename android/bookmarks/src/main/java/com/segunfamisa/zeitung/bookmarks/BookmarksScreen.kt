@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalCoilApi::class
)

package com.segunfamisa.zeitung.bookmarks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.common.LocalAppState
import com.segunfamisa.zeitung.common.WindowStyle
import com.segunfamisa.zeitung.common.design.NewsCard
import com.segunfamisa.zeitung.common.theme.colorScheme
import com.segunfamisa.zeitung.news.ui.NewsUiItem
import com.segunfamisa.zeitung.news.ui.NewsUiState

internal const val TEST_TAG_LOADING = "Loading Indicator"
internal const val TEST_TAG_ARTICLE_LIST = "Saved articles list"

@Composable
internal fun BookmarksContent(
    uiState: NewsUiState,
    onBookmarkRemoved: ((String) -> Unit) = { _ -> }
) {
    when (uiState) {
        is NewsUiState.Loaded -> NewsArticleList(
            header = uiState.header,
            newsItems = uiState.news,
            onNewsItemSaved = onBookmarkRemoved
        )

        is NewsUiState.Loading -> LoadingScreen()
        is NewsUiState.Error -> ErrorSnackbar()
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
private fun NewsArticleList(
    header: NewsUiItem? = null,
    newsItems: List<NewsUiItem> = emptyList(),
    windowStyle: WindowStyle = LocalAppState.current.windowStyle,
    onNewsItemClicked: ((String) -> Unit)? = null,
    onNewsItemSaved: ((String) -> Unit)? = null,
) {
    val articleCard = @Composable { newsUiItem: NewsUiItem ->
        NewsCard(
            title = newsUiItem.headline,
            description = newsUiItem.subhead,
            url = newsUiItem.url,
            imageUrl = newsUiItem.imageUrl,
            author = newsUiItem.author,
            source = newsUiItem.source,
            date = newsUiItem.date,
            saved = newsUiItem.saved,
            modifier = Modifier
                .clickable(onClick = { onNewsItemClicked?.invoke(newsUiItem.url) }),
            onSaveClicked = { url, _ ->
                onNewsItemSaved?.invoke(url)
            },
        )
    }

    when (windowStyle) {
        WindowStyle.OnePane -> NewsList(header = header, newsItems = newsItems) {
            articleCard(it)
        }

        WindowStyle.TwoPane -> NewsGrid(header = header, newsItems = newsItems) {
            articleCard(it)
        }
    }

}

@Composable
private fun NewsGrid(
    header: NewsUiItem?,
    newsItems: List<NewsUiItem>,
    columns: Int = 2,
    listItem: @Composable (NewsUiItem) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        content = {
            header?.let {
                item {
                    listItem(it)
                }
            }
            items(newsItems) {
                listItem(it)
            }
        },
        modifier = Modifier
            .padding(bottom = 64.dp, top = 56.dp)
            .testTag(TEST_TAG_ARTICLE_LIST)
    )
}

@Composable
private fun NewsList(
    header: NewsUiItem?,
    newsItems: List<NewsUiItem>,
    listItem: @Composable (NewsUiItem) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .padding(bottom = 64.dp, top = 56.dp)
            .testTag(TEST_TAG_ARTICLE_LIST)
    ) {
        header?.let {
            item {
                listItem(it)
            }
        }

        items(newsItems) {
            listItem(it)
        }
    }
}

@Composable
private fun ErrorSnackbar() {
    //TODO("Not yet implemented")
}


@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .testTag(TEST_TAG_LOADING),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = colorScheme().secondary)
    }
}
