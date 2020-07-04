package com.segunfamisa.zeitung.ui.news

import androidx.ui.graphics.ImageAsset
import java.util.*

data class UiNewsItem(
    val headline: String,
    val subhead: String,
    val author: String,
    val date: Date,
    val url: String,
    val image: ImageAsset?,
    val source: UiSourceItem
)

data class UiSourceItem(
    val id: String,
    val name: String,
    val logo: ImageAsset?
)
