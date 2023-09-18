@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class, ExperimentalCoilApi::class
)

package com.segunfamisa.zeitung.news

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.common.LocalAppState
import com.segunfamisa.zeitung.common.WindowStyle
import com.segunfamisa.zeitung.common.design.NewsCard
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.common.theme.colorScheme
import com.segunfamisa.zeitung.common.theme.preview.ThemedPreview
import com.segunfamisa.zeitung.news.ui.NewsUiItem
import com.segunfamisa.zeitung.news.ui.NewsUiState

internal const val TEST_TAG_LOADING = "Loading Indicator"
internal const val TEST_TAG_ARTICLE_LIST = "News list"

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun NewsContent(
    uiState: NewsUiState,
    onNewsItemSaved: ((String, Boolean) -> Unit) = { _, _ -> }
) {
    when (uiState) {
        is NewsUiState.Loaded -> NewsArticleList(
            header = uiState.header,
            newsItems = uiState.news,
            onNewsItemSaved = onNewsItemSaved
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
    onNewsItemSaved: ((String, Boolean) -> Unit)? = null,
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
                .padding(bottom = 16.dp)
                .clickable(onClick = { onNewsItemClicked?.invoke(newsUiItem.url) }),
            onSaveClicked = { url, saved ->
                onNewsItemSaved?.invoke(url, saved)
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
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp),
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
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxSize()
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

@Composable
private fun ErrorSnackbar() {
    // TODO("Not yet implemented")
}

@Composable
@Preview("Loading screen")
private fun PreviewLoadingState() {
    ThemedPreview {
        LoadingScreen()
    }
}

@OptIn(ExperimentalCoilApi::class)
@ExperimentalComposeUiApi
@Composable
@Preview("News article list")
private fun PreviewNewsArticleList() {
    ZeitungTheme {
        val article = fakeArticle()
        val articles = listOf(
            article.copy(source = null),
            article.copy(saved = false),
            article.copy(imageUrl = null)
        )
        NewsArticleList(newsItems = articles)
    }
}

@Composable
@Preview(
    "News article two pane",
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait"
)
private fun PreviewNewsArticleGrid() {
    ZeitungTheme {
        val article = fakeArticle()
        val articles = listOf(
            article.copy(source = null),
            article.copy(saved = false),
            article.copy(imageUrl = null)
        )
        NewsArticleList(newsItems = articles, windowStyle = WindowStyle.TwoPane)
    }
}

@OptIn(ExperimentalCoilApi::class)
@ExperimentalComposeUiApi
@Composable
@Preview("News article list (dark theme)")
private fun PreviewDarkThemeNewsArticleList() {
    ZeitungTheme(darkTheme = true) {
        val articles = listOf(fakeArticle(), fakeArticle().copy(saved = false), fakeArticle())
        NewsArticleList(newsItems = articles) { _, _ -> }
    }
}

@Composable
private fun fakeArticle() = NewsUiItem(
    source = "BBC",
    author = "Nintendo Life",
    headline = "The World Ends With You: The Animation Airs In 2021, Here's Your First Look - Nintendo Life",
    subhead = "Square Enix ' s hit game returns as an anime",
    url = "https://www.nintendolife.com/news/2020/07/the_world_ends_with_you_the_animation_airs_in_2021_heres_your_first_look",
    saved = true,
    imageUrl = "https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5ee95df165be0e00060a8bdd%2F0x0.jpg%3FcropX1%3D12%26cropX2%3D695%26cropY1%3D9%26cropY2%3D393",
    date = "2 hours ago"
)
