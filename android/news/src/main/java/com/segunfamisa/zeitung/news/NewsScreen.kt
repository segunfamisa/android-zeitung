package com.segunfamisa.zeitung.news

import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.common.design.NewsCard
import com.segunfamisa.zeitung.common.getTimeAgo
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.common.theme.colorScheme
import com.segunfamisa.zeitung.common.theme.preview.ThemedPreview
import java.util.Date

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
    onNewsItemClicked: ((String) -> Unit)? = null,
    onNewsItemSaved: ((String, Boolean) -> Unit)? = null,
) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 64.dp, top = 56.dp)
            .testTag(TEST_TAG_ARTICLE_LIST)
    ) {

        header?.let {
            item {
                NewsCard(
                    title = it.headline,
                    description = it.subhead,
                    url = it.url,
                    imageUrl = it.imageUrl,
                    author = it.author,
                    source = it.source,
                    date = it.date,
                    saved = it.saved,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable(onClick = { onNewsItemClicked?.invoke(it.url) }),
                    onSaveClicked = { url, saved ->
                        onNewsItemSaved?.invoke(url, saved)
                    },
                )
            }
        }

        items(newsItems) {
            NewsCard(
                title = it.headline,
                description = it.subhead,
                url = it.url,
                imageUrl = it.imageUrl,
                author = it.author,
                source = it.source,
                date = it.date,
                saved = it.saved,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable(onClick = { onNewsItemClicked?.invoke(it.url) }),
                onSaveClicked = { url, saved ->
                    onNewsItemSaved?.invoke(url, saved)
                },
            )
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

private fun Date.asTimeAgo(resources: Resources): String {
    return getTimeAgo(this.time, System.currentTimeMillis(), resources)
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
