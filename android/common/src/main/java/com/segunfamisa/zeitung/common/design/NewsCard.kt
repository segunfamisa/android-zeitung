package com.segunfamisa.zeitung.common.design

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.common.NetworkImage
import com.segunfamisa.zeitung.common.R
import com.segunfamisa.zeitung.common.theme.colorScheme
import com.segunfamisa.zeitung.common.theme.preview.ThemedPreview
import com.segunfamisa.zeitung.common.theme.typography
import java.util.Locale

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(
    title: String,
    description: String?,
    url: String,
    imageUrl: String?,
    author: String?,
    source: String?,
    date: String?,
    modifier: Modifier = Modifier,
    saved: Boolean,
    onSaveClicked: (String, Boolean) -> Unit = { _, _ -> },
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = colorScheme().surface),
        onClick = onClick
    ) {
        Column {
            imageUrl?.let {
                NetworkImage(
                    url = imageUrl,
                    modifier = Modifier
                        .aspectRatio(1.78f)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                            )
                        ),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Box(modifier = Modifier.padding(16.dp)) {
            val typography = typography()
            Column {
                Row {
                    source?.let {
                        Text(
                            text = source,
                            style = typography.labelMedium.copy(color = colorScheme().secondary),
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }
                    if (!author.isNullOrEmpty()) {
                        if (source != null) {
                            Text(
                                text = '\u2E31'.toString(),
                                style = typography.labelMedium.copy(color = colorScheme().secondary),
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                        }
                        Text(
                            text = author,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = typography.labelMedium.copy(color = colorScheme().secondary),
                        )
                    }
                }
                Row {
                    Text(
                        text = title,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .weight(1f),
                        style = typography.titleMedium
                    )

                    SaveButton(
                        isSaved = saved,
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp)
                            .size(24.dp),
                        onSaveClicked = {
                            onSaveClicked(url, it)
                        },
                    )
                }
                description?.let {
                    Text(
                        text = description,
                        style = typography.bodyMedium.copy(color = colorScheme().secondary),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                date?.let {
                    Text(
                        text = date.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        modifier = Modifier.padding(top = 4.dp),
                        style = typography.labelMedium.copy(color = colorScheme().secondary),
                    )
                }
            }
        }

    }
}


@Composable
private fun SaveButton(
    isSaved: Boolean,
    modifier: Modifier,
    onSaveClicked: (Boolean) -> Unit = { _ -> }
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

@Preview
@Composable
fun PreviewNewsCard() {
    ThemedPreview {
        NewsCard(
            title = "The World Ends With You: The Animation Airs In 2021, Here's Your First Look - Nintendo Life",
            description = "Square Enix ' s hit game returns as an anime",
            imageUrl = "https://ichef.bbci.co.uk/news/1024/branded_news/1540A/production/_131105078_derna2.jpg",
            url = "https://www.nintendolife.com/news/2020/07/the_world_ends_with_you_the_animation_airs_in_2021_heres_your_first_look",
            source = "BBC",
            author = "Segun Famisa, Segun Famisa, Segun Famisa and Segun Famisa",
            date = "2 hours ago",
            saved = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
