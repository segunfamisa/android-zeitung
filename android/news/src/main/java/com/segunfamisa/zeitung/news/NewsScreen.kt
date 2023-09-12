package com.segunfamisa.zeitung.news

import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.common.NetworkImage
import com.segunfamisa.zeitung.common.getTimeAgo
import com.segunfamisa.zeitung.common.theme.colorScheme
import com.segunfamisa.zeitung.common.theme.preview.ThemedPreview
import com.segunfamisa.zeitung.common.theme.shapes
import com.segunfamisa.zeitung.common.theme.typography
import java.util.Date
import java.util.Locale

internal const val TEST_TAG_LOADING = "Loading Indicator"

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun NewsContent(uiState: NewsUiState) {
    when (uiState) {
        is NewsUiState.Loaded -> NewsArticleList(header = uiState.header, newsItems = uiState.news)
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
        modifier = Modifier.padding(bottom = 56.dp)
    ) {

        header?.let {
            item {
                TopNewsArticleItem(
                    item = it,
                    modifier = Modifier.clickable(onClick = { onNewsItemClicked?.invoke(it.url) }),
                    onSaveClicked = { url, saved ->
                        onNewsItemSaved?.invoke(url, saved)
                    }
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }

        items(newsItems) {
            NewsArticleItem(
                item = it,
                modifier = Modifier.clickable(onClick = { onNewsItemClicked?.invoke(it.url) }),
                onSaveClicked = { url, saved ->
                    onNewsItemSaved?.invoke(url, saved)
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@ExperimentalCoilApi
@Composable
@ExperimentalComposeUiApi
private fun TopNewsArticleItem(
    item: NewsUiItem,
    modifier: Modifier,
    onSaveClicked: (String, Boolean) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        val (headline, source, image, date, save) = createRefs()
        val typography = typography()

        ArticleImage(url = item.imageUrl,
            modifier = Modifier
                .aspectRatio(1.78f)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp,
                    )
                )
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        item.source?.let {
            Text(text = item.source,
                style = typography.labelSmall,
                modifier = Modifier.constrainAs(source) {
                    top.linkTo(image.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                })
        }

        Text(text = item.headline,
            style = typography.titleLarge,
            modifier = Modifier.constrainAs(headline) {
                top.linkTo(source.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })

        item.date?.let { timeAgo ->
            Text(text = timeAgo.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = typography.labelMedium,
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(headline.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(save.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                })
        }


        SaveButton(
            isSaved = item.saved,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(save) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }, onSaveClicked = { saved ->
                onSaveClicked(item.url, saved)
            }
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun NewsArticleItem(
    item: NewsUiItem,
    modifier: Modifier,
    onSaveClicked: (String, Boolean) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        val (headline, source, image, date, save) = createRefs()
        val typography = typography()
        val shapes = shapes()

        item.source?.let {
            Text(
                text = item.source,
                style = typography.labelSmall,
                modifier = Modifier.constrainAs(source) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(image.start, 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )
        }

        Text(text = item.headline,
            style = typography.titleMedium,
            modifier = Modifier.constrainAs(headline) {
                top.linkTo(source.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(image.start, 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })

        item.date?.let {
            Text(
                text = item.date.capitalize(Locale.getDefault()),
                style = typography.labelMedium,
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(headline.bottom, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(image.start, 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )
        }


        ArticleImage(
            url = item.imageUrl,
            modifier = Modifier
                .size(64.dp)
                .clip(shapes.medium)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                })

        SaveButton(
            isSaved = item.saved,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(save) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }, onSaveClicked = { saved ->
                onSaveClicked(item.url, saved)
            })
    }
}

@ExperimentalCoilApi
@Composable
private fun ArticleImage(
    url: String?,
    modifier: Modifier
) {
    url?.let {
        NetworkImage(
            url = url, modifier = modifier, contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun SaveButton(
    isSaved: Boolean, modifier: Modifier, onSaveClicked: (Boolean) -> Unit
) {
    IconToggleButton(
        checked = isSaved, onCheckedChange = { onSaveClicked(it) }, modifier = modifier
    ) {
        if (isSaved) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = null,
                tint = colorScheme().primary
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark_outlined),
                contentDescription = null
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

@Composable
@Preview("News article item")
private fun PreviewNewsArticleItem() {
    ThemedPreview {
        NewsArticleItem(item = fakeArticle(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onSaveClicked = { _, _ -> })
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
@Preview("Top news article item")
private fun PreviewTopNewsArticleItem() {
    ThemedPreview {
        TopNewsArticleItem(
            item = fakeArticle(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onSaveClicked = { _, _ -> })
    }
}

@Composable
@Preview("News article item (dark theme)")
private fun PreviewDarkThemeNewsArticleItem() {
    ThemedPreview(darkTheme = true) {
        NewsArticleItem(item = fakeArticle(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onSaveClicked = { _, _ -> })
    }
}

@OptIn(ExperimentalCoilApi::class)
@ExperimentalComposeUiApi
@Composable
@Preview("News article list")
private fun PreviewNewsArticleList() {
    ThemedPreview {
        val article = fakeArticle()
        val articles = listOf(article, article.copy(saved = false), article.copy(imageUrl = null))
        NewsArticleList(newsItems = articles)
    }
}

@OptIn(ExperimentalCoilApi::class)
@ExperimentalComposeUiApi
@Composable
@Preview("News article list (dark theme)")
private fun PreviewDarkThemeNewsArticleList() {
    ThemedPreview(darkTheme = true) {
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
