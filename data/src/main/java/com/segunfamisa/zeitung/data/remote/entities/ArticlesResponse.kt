package com.segunfamisa.zeitung.data.remote.entities

import com.squareup.moshi.Json

internal data class ArticlesResponse(
    @field:Json(name = "totalResults") val totalResults: Long,
    @field:Json(name = "articles") val articles: List<Article>
)
