package com.segunfamisa.zeitung.ui.news

import android.content.res.Resources
import android.util.Log
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.ui.core.*
import androidx.ui.core.gesture.longPressGestureFilter
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.Divider
import androidx.ui.material.IconToggleButton
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.di.NewsContainer
import com.segunfamisa.zeitung.ui.UiState
import com.segunfamisa.zeitung.ui.theme.secondary
import com.segunfamisa.zeitung.ui.theme.shapes
import com.segunfamisa.zeitung.ui.theme.typography
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
fun NewsArticleList(
    articles: List<UiNewsItem>,
    onItemClicked: (UiNewsItem) -> Unit,
    onSaveClicked: (UiNewsItem, Boolean) -> Unit
) {
    VerticalScroller() {
        articles.forEachIndexed { index, item ->
            NewsArticleItem(
                item,
                Modifier.clickable(onClick = { onItemClicked(item) }).longPressGestureFilter { },
                onSaveClicked
            )

            if (index < articles.size - 1) {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
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
            text = item.date.asTimeAgo(ContextAmbient.current.resources).capitalize(),
            style = typography.caption,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(headline.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(image.start, 8.dp)
                width = Dimension.fillToConstraints
            }
        )

        item.image?.let {
            Image(
                asset = it,
                modifier = Modifier.preferredSize(64.dp)
                    .clip(shapes.medium)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                contentScale = ContentScale.Crop
            )
        }

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
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
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
        val articles = listOf(fakeArticle(), fakeArticle().copy(isSaved = false), fakeArticle())
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
