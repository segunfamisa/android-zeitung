package com.segunfamisa.zeitung.util.common

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.platform.ContextAmbient
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.news.UiNewsItem
import com.segunfamisa.zeitung.news.UiSourceItem
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import java.util.*

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    children: @Composable() () -> Unit
) {
    ZeitungTheme(darkTheme = darkTheme) {
        Surface {
            children()
        }
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
    image = imageFromResource(
        ContextAmbient.current.resources,
        R.drawable.nintendo
    ),
    imageUrl = "https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5ee95df165be0e00060a8bdd%2F0x0.jpg%3FcropX1%3D12%26cropX2%3D695%26cropY1%3D9%26cropY2%3D393",
    date = Date()
)
