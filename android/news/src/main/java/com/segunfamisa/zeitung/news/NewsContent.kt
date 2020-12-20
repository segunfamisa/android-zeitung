package com.segunfamisa.zeitung.news

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.gesture.longPressGestureFilter
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.segunfamisa.zeitung.common.NetworkImage
import com.segunfamisa.zeitung.common.UiState
import com.segunfamisa.zeitung.common.getTimeAgo
import com.segunfamisa.zeitung.common.theme.colors
import com.segunfamisa.zeitung.common.theme.preview.ThemedPreview
import com.segunfamisa.zeitung.common.theme.shapes
import com.segunfamisa.zeitung.common.theme.typography
import java.util.*

const val LOG_TAG = "NewsContent"

@Composable
fun NewsContent(
    newsViewModel: Lazy<NewsViewModel>,
    onItemClicked: (UiNewsItem) -> Unit
) {
    val viewModel = newsViewModel.value
    val uiState: UiState<List<UiNewsItem>>? by viewModel.state.observeAsState()
    val onSaveClicked: (UiNewsItem, Boolean) -> Unit = { newsItem, saved ->
        Log.i(LOG_TAG, "onSaveClicked: item: $newsItem, shouldSave: $saved")
        viewModel.saveNewsItem(newsItem, saved)
    }
    Column {
        when (uiState) {
            is UiState.Success<List<UiNewsItem>> -> NewsArticleList(
                articles = (uiState as UiState.Success<List<UiNewsItem>>).data,
                onItemClicked = onItemClicked,
                onSaveClicked = onSaveClicked
            )
            is UiState.Loading -> LoadingScreen()
            is UiState.Error -> ErrorSnackbar()
        }
    }
}

@Composable
private fun NewsArticleList(
    articles: List<UiNewsItem>,
    onItemClicked: (UiNewsItem) -> Unit,
    onSaveClicked: (UiNewsItem, Boolean) -> Unit
) {
    ScrollableColumn(
        modifier = Modifier.padding(bottom = 56.dp)
    ) {
        articles.forEachIndexed { index, item ->
            if (item is UiNewsItem.Top) {
                TopNewsArticleItem(
                    item,
                    Modifier.clickable(onClick = { onItemClicked(item) })
                        .longPressGestureFilter { },
                    onSaveClicked
                )
            } else {
                NewsArticleItem(
                    item,
                    Modifier.clickable(onClick = { onItemClicked(item) })
                        .longPressGestureFilter { },
                    onSaveClicked
                )
            }

            if (index < articles.size - 1) {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
private fun TopNewsArticleItem(
    item: UiNewsItem,
    modifier: Modifier,
    onSaveClicked: (UiNewsItem, Boolean) -> Unit
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
    ) {
        val (headline, source, image, date, save) = createRefs()
        val typography = typography()

        ArticleImage(
            item = item,
            modifier = Modifier.aspectRatio(1.78f).fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topLeft = 4.dp,
                        topRight = 4.dp,
                        bottomRight = 0.dp,
                        bottomLeft = 0.dp
                    )
                )
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = item.source.name,
            style = typography.overline,
            modifier = Modifier.constrainAs(source) {
                top.linkTo(image.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        Text(
            text = item.headline,
            style = typography.h6,
            modifier = Modifier.constrainAs(headline) {
                top.linkTo(source.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        Text(
            text = item.date.asTimeAgo(ContextAmbient.current.resources)
                .capitalize(Locale.getDefault()),
            style = typography.caption,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(headline.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(save.start)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        SaveButton(
            isSaved = item.isSaved,
            modifier = Modifier.preferredSize(24.dp)
                .constrainAs(save) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            onSaveClicked = { saved ->
                onSaveClicked(item, saved)
            }
        )
    }
}

@Composable
private fun NewsArticleItem(
    item: UiNewsItem,
    modifier: Modifier,
    onSaveClicked: (UiNewsItem, Boolean) -> Unit
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
    ) {
        val (headline, source, image, date, save) = createRefs()
        val typography = typography()
        val shapes = shapes()

        Text(
            text = item.source.name,
            style = typography.overline,
            modifier = Modifier.constrainAs(source) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(image.start, 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        Text(
            text = item.headline,
            style = typography.h6,
            modifier = Modifier.constrainAs(headline) {
                top.linkTo(source.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(image.start, 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        Text(
            text = item.date
                .asTimeAgo(ContextAmbient.current.resources)
                .capitalize(Locale.getDefault()),
            style = typography.caption,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(headline.bottom, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(image.start, 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        ArticleImage(
            item = item,
            modifier = Modifier.preferredSize(64.dp)
                .clip(shapes.medium)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        SaveButton(
            isSaved = item.isSaved,
            modifier = Modifier.preferredSize(24.dp)
                .constrainAs(save) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            onSaveClicked = { saved ->
                onSaveClicked(item, saved)
            }
        )
    }
}

@Composable
private fun ArticleImage(
    item: UiNewsItem,
    modifier: Modifier
) {
    item.image?.let {
        Image(
            imageVector = it,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } ?: NetworkImage(
        url = item.imageUrl,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun SaveButton(
    isSaved: Boolean,
    modifier: Modifier,
    onSaveClicked: (Boolean) -> Unit
) {
    IconToggleButton(
        checked = isSaved,
        onCheckedChange = { onSaveClicked(it) },
        modifier = modifier
    ) {
        if (isSaved) {
            Icon(
                imageVector = vectorResource(id = R.drawable.ic_bookmark),
                tint = colors().secondary
            )
        } else {
            Icon(
                imageVector = vectorResource(id = R.drawable.ic_bookmark_outlined)
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = colors().secondary)
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
        NewsArticleItem(
            item = fakeArticle(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            onSaveClicked = { _, _ -> }
        )
    }
}

@Composable
@Preview("Top news article item")
private fun PreviewTopNewsArticleItem() {
    ThemedPreview {
        TopNewsArticleItem(
            item = fakeArticle(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            onSaveClicked = { _, _ -> }
        )
    }
}

@Composable
@Preview("News article item (dark theme)")
private fun PreviewDarkThemeNewsArticleItem() {
    ThemedPreview(darkTheme = true) {
        NewsArticleItem(
            item = fakeArticle(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            onSaveClicked = { _, _ -> }
        )
    }
}

@Composable
@Preview("News article list")
private fun PreviewNewsArticleList() {
    ThemedPreview {
        val article = fakeArticle()
        val articles = listOf(article, article.copy(isSaved = false), article.copy(image = null))
        NewsArticleList(
            articles = articles,
            onItemClicked = { },
            onSaveClicked = { _, _ -> }
        )
    }
}

@Composable
@Preview("News article list (dark theme)")
private fun PreviewDarkThemeNewsArticleList() {
    ThemedPreview(darkTheme = true) {
        val articles = listOf(fakeArticle(), fakeArticle().copy(isSaved = false), fakeArticle())
        NewsArticleList(
            articles = articles,
            onItemClicked = { },
            onSaveClicked = { _, _ -> }
        )
    }
}

@Composable
fun fakeArticle() = UiNewsItem.Regular(
    source = UiSourceItem(
        id = "",
        name = "Nintendo Life",
        logo = null
    ),
    author = "Nintendo Life",
    headline = "The World Ends With You: The Animation Airs In 2021, Here's Your First Look - Nintendo Life",
    subhead = "Square Enix ' s hit game returns as an anime",
    url = "https://www.nintendolife.com/news/2020/07/the_world_ends_with_you_the_animation_airs_in_2021_heres_your_first_look",
    isSaved = true,
    image = null,
    imageUrl = "https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5ee95df165be0e00060a8bdd%2F0x0.jpg%3FcropX1%3D12%26cropX2%3D695%26cropY1%3D9%26cropY2%3D393",
    date = Date()
)
