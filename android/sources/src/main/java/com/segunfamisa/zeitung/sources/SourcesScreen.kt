package com.segunfamisa.zeitung.sources

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segunfamisa.zeitung.common.theme.colorScheme
import com.segunfamisa.zeitung.common.theme.preview.ThemedPreview
import com.segunfamisa.zeitung.common.theme.typography
import com.segunfamisa.zeitung.sources.internal.UiItem
import com.segunfamisa.zeitung.sources.internal.UiState
import java.util.Locale

internal const val TEST_TAG_LOADING = "loading"

@Composable
internal fun SourcesContent(
    uiState: UiState,
    onSourceFollowed: (String, Boolean) -> Unit = { _, _ -> },
) {
    when {
        uiState.loading -> LoadingScreen()
        uiState.error != null -> {
            Toast.makeText(LocalContext.current, uiState.error, Toast.LENGTH_SHORT).show()
        }

        else -> SourcesList(sources = uiState.sources, onSourceFollowed = onSourceFollowed)
    }
}

@Composable
private fun SourcesList(sources: List<UiItem>, onSourceFollowed: (String, Boolean) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 64.dp, top = 56.dp)
    ) {
        items(sources) {
            when (it) {
                is UiItem.Section -> SourceSection(it)
                is UiItem.Source -> SourceItem(source = it, onSourceFollowed = onSourceFollowed)
            }
        }
    }
}

@Composable
private fun SourceItem(
    source: UiItem.Source,
    onSourceClicked: () -> Unit = {},
    onSourceFollowed: (String, Boolean) -> Unit = { _, _ -> }
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onSourceClicked)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = source.name, style = typography().titleMedium)
            Text(
                text = source.description,
                style = typography().labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconToggleButton(
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            checked = source.followed,
            onCheckedChange = {
                onSourceFollowed(source.id, it)
            }
        ) {
            if (source.followed) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    tint = colorScheme().secondary,
                    contentDescription = stringResource(R.string.content_desc_unfollow, source.name)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    tint = colorScheme().surfaceVariant,
                    contentDescription = stringResource(R.string.content_desc_follow, source.name)
                )
            }
        }
    }
}

@Composable
private fun SourceSection(section: UiItem.Section) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = section.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
        style = typography().headlineSmall
    )
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

@Preview
@Composable
private fun ListPreview() {
    ThemedPreview {
        SourcesList(
            sources = listOf(
                UiItem.Section(name = "Entertainment"),
                UiItem.Source(
                    id = "",
                    name = "BBC",
                    description = "Description",
                    url = "",
                    followed = true
                ),
                UiItem.Source(
                    id = "",
                    name = "CNN",
                    description = "Longer description that goes all the way to enter multiple lines" +
                        " and then we want to try and have something more than two lines." +
                        " So this way we can test everything",
                    url = "",
                    followed = false
                )
            ),
            onSourceFollowed = { _, _ -> }
        )
    }
}

@Preview
@Composable
private fun ListPreviewDarkTheme() {
    ThemedPreview(darkTheme = true) {
        SourcesList(
            sources = listOf(
                UiItem.Section(name = "Entertainment"),
                UiItem.Source(
                    id = "",
                    name = "BBC",
                    description = "Description",
                    url = "",
                    followed = true
                ),
                UiItem.Source(
                    id = "",
                    name = "CNN",
                    description = "Longer description that goes all the way to enter multiple lines" +
                        " and then we want to try and have something more than two lines." +
                        " So this way we can test everything",
                    url = "",
                    followed = false
                )
            ),
            onSourceFollowed = { _, _ -> }
        )
    }
}
