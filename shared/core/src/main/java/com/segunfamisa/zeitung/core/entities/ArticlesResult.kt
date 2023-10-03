package com.segunfamisa.zeitung.core.entities

data class ArticlesResult(
    val totalResults: Long,
    val articles: List<Article>
)
