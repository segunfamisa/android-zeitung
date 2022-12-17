package com.segunfamisa.zeitung.news

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.common.NetworkImage
import com.segunfamisa.zeitung.common.UiState
import com.segunfamisa.zeitung.common.getTimeAgo
import com.segunfamisa.zeitung.common.theme.colorScheme
import com.segunfamisa.zeitung.common.theme.preview.ThemedPreview
import com.segunfamisa.zeitung.common.theme.preview.ThemedUiPreview
import com.segunfamisa.zeitung.common.theme.shapes
import com.segunfamisa.zeitung.common.theme.typography
import java.util.*

const val LOG_TAG = "NewsContent"

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun NewsContent(
    uiState: UiState<List<UiNewsItem>>? = null,
    onItemClicked: (UiNewsItem) -> Unit,
    onSaveClicked: (UiNewsItem, Boolean) -> Unit,
) {
    Column {
        when (uiState) {
            is UiState.Success<List<UiNewsItem>> -> NewsArticleList(
                articles = uiState.data,
                onItemClicked = onItemClicked,
                onSaveClicked = onSaveClicked
            )
            is UiState.Loading -> LoadingScreen()
            is UiState.Error -> ErrorSnackbar()
            else -> {}
        }
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
private fun NewsArticleList(
    articles: List<UiNewsItem>,
    onItemClicked: (UiNewsItem) -> Unit,
    onSaveClicked: (UiNewsItem, Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(
                state = scrollState
            )
            .padding(bottom = 56.dp)
    ) {
        articles.forEachIndexed { index, item ->
            if (item is UiNewsItem.Top) {
                TopNewsArticleItem(
                    item,
                    Modifier.clickable(onClick = { onItemClicked(item) }),
                    onSaveClicked
                )
            } else {
                NewsArticleItem(
                    item,
                    Modifier.clickable(onClick = { onItemClicked(item) }),
                    onSaveClicked
                )
            }

            if (index < articles.size - 1) {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
@ExperimentalComposeUiApi
private fun TopNewsArticleItem(
    item: UiNewsItem,
    modifier: Modifier,
    onSaveClicked: (UiNewsItem, Boolean) -> Unit
) {
    val resources = LocalContext.current.resources

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        val (headline, source, image, date, save) = createRefs()
        val typography = typography()

        ArticleImage(
            item = item,
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
                }
        )

        Text(
            text = item.source.name,
            style = typography.labelSmall,
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
            style = typography.titleLarge,
            modifier = Modifier.constrainAs(headline) {
                top.linkTo(source.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        Text(
            text = item.date.asTimeAgo(resources)
                .capitalize(Locale.getDefault()),
            style = typography.labelMedium,
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
            modifier = Modifier
                .size(24.dp)
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
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        val (headline, source, image, date, save) = createRefs()
        val typography = typography()
        val shapes = shapes()

        Text(
            text = item.source.name,
            style = typography.labelSmall,
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
            style = typography.titleMedium,
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
                .asTimeAgo(LocalContext.current.resources)
                .capitalize(Locale.getDefault()),
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

        ArticleImage(
            item = item,
            modifier = Modifier
                .size(64.dp)
                .clip(shapes.medium)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        SaveButton(
            isSaved = item.isSaved,
            modifier = Modifier
                .size(24.dp)
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

@ExperimentalCoilApi
@Composable
private fun ArticleImage(
    item: UiNewsItem,
    modifier: Modifier
) {
    item.image?.let {
        Image(
            painter = it,
            contentDescription = null,
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
            .wrapContentSize(Alignment.Center),
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

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
@ThemedUiPreview
private fun PreviewTopNewsArticleItem() {
    ThemedPreview {
        TopNewsArticleItem(
            item = fakeArticle(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onSaveClicked = { _, _ -> }
        )
    }
}

@Composable
@ThemedUiPreview
private fun PreviewRegularNewsArticleItem() {
    ThemedPreview {
        NewsArticleItem(
            item = fakeArticle(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onSaveClicked = { _, _ -> }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
@ThemedUiPreview
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
    image = painterResource(id = R.drawable.nintendo),
    imageUrl = "https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5ee95df165be0e00060a8bdd%2F0x0.jpg%3FcropX1%3D12%26cropX2%3D695%26cropY1%3D9%26cropY2%3D393",
    date = Date()
)
