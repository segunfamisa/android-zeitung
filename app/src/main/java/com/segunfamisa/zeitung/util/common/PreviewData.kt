package com.segunfamisa.zeitung.util.common

import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import androidx.ui.graphics.imageFromResource
import androidx.ui.material.Surface
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.ui.news.UiNewsItem
import com.segunfamisa.zeitung.ui.news.UiSourceItem
import com.segunfamisa.zeitung.ui.theme.ZeitungTheme
import java.util.*

@Composable
internal fun ThemedPreview(
    children: @Composable() () -> Unit
) {
    ZeitungTheme {
        Surface {
            children()
        }
    }
}

@Composable
fun fakeArticle() = UiNewsItem(
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
    ), //"https://images.nintendolife.com/973848f42faa0/1280x720.jpg",
    date = Date()
)
