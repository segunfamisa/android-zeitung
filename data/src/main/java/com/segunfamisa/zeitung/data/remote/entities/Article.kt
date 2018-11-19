package com.segunfamisa.zeitung.data.remote.entities

import com.squareup.moshi.Json
import java.util.Date

internal data class Article(
    @field:Json(name = "source") val source: SourceMinimal,
    @field:Json(name = "author") val author: String?,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "urlToImage") val imageUrl: String?,
    @field:Json(name = "publishedAt") val publishedAt: Date,
    @field:Json(name = "content") val content: String?
)
