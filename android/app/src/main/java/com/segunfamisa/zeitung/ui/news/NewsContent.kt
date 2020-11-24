package com.segunfamisa.zeitung.ui.news

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.gesture.longPressGestureFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.di.NewsContainer
import com.segunfamisa.zeitung.news.UiNewsItem
import com.segunfamisa.zeitung.common.UiState
import com.segunfamisa.zeitung.common.theme.secondary
import com.segunfamisa.zeitung.common.theme.shapes
import com.segunfamisa.zeitung.common.theme.typography
import com.segunfamisa.zeitung.util.NetworkImage
import com.segunfamisa.zeitung.util.common.ThemedPreview
import com.segunfamisa.zeitung.util.common.fakeArticle
import com.segunfamisa.zeitung.util.getTimeAgo
import java.util.*

const val LOG_TAG = "NewsContent"

@Composable
fun NewsContent(
    newsContainer: NewsContainer,
    onItemClicked: (UiNewsItem) -> Unit
) {
    val viewModel = newsContainer.newsViewModel.value
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
            asset = it,
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
                asset = vectorResource(id = R.drawable.ic_bookmark),
                tint = secondary()
            )
        } else {
            Icon(
                asset = vectorResource(id = R.drawable.ic_bookmark_outlined)
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
        alignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = secondary())
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
