package com.segunfamisa.zeitung.news

sealed interface NewsUiState {

    object Loading : NewsUiState

    data class Loaded(val header: NewsUiItem?, val news: List<NewsUiItem>) : NewsUiState

    data class Error(val message: String) : NewsUiState
}

data class NewsUiItem(
    val headline: String,
    val subhead: String?,
    val author: String?,
    val date: String?,
    val url: String,
    val saved: Boolean,
    val imageUrl: String?,
    val source: NewsSourceUiItem?,
)

typealias NewsSourceUiItem = String