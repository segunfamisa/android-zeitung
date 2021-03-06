package com.segunfamisa.zeitung.news

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import java.util.*

sealed class UiNewsItem(
    open val headline: String,
    open val subhead: String,
    open val author: String,
    open val date: Date,
    open val url: String,
    open val isSaved: Boolean,
    open val image: Painter?,
    open val imageUrl: String,
    open val source: UiSourceItem
) {

    data class Regular(
        override val headline: String,
        override val subhead: String,
        override val author: String,
        override val date: Date,
        override val url: String,
        override val isSaved: Boolean,
        override val image: Painter?,
        override val imageUrl: String,
        override val source: UiSourceItem
    ) : UiNewsItem(
        headline,
        subhead,
        author,
        date,
        url,
        isSaved,
        image,
        imageUrl,
        source
    )

    data class Top(
        override val headline: String,
        override val subhead: String,
        override val author: String,
        override val date: Date,
        override val url: String,
        override val isSaved: Boolean,
        override val image: Painter?,
        override val imageUrl: String,
        override val source: UiSourceItem
    ) : UiNewsItem(
        headline,
        subhead,
        author,
        date,
        url,
        isSaved,
        image,
        imageUrl,
        source
    )
}

data class UiSourceItem(
    val id: String,
    val name: String,
    val logo: ImageBitmap?
)
