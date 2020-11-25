package com.segunfamisa.zeitung.core.entities

import java.util.Date

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val publishedAt: Date,
    val content: String,
    val isSaved: Boolean
)
