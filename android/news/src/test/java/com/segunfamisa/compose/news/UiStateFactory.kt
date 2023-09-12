package com.segunfamisa.compose.news

import com.segunfamisa.zeitung.news.NewsUiItem
import com.segunfamisa.zeitung.news.NewsUiState

object UiStateFactory {

    fun createLoadedState(
        header: NewsUiItem? = null,
        news: List<NewsUiItem> = emptyList()
    ): NewsUiState {
        return NewsUiState.Loaded(
            header = header ?: NewsUiItem(
                headline = "Breaking header news",
                subhead = null,
                imageUrl = "https://images.google.com",
                author = "Segun header",
                date = "Yesterday",
                url = "https://google.com",
                source = "Header news",
                saved = false,
            ),
            news = news
        )
    }

    fun createLoadedState(
        header: NewsUiItem? = NewsUiItem(
            headline = "Breaking header news",
            subhead = null,
            imageUrl = "https://images.google.com",
            author = "Segun header",
            date = "Yesterday",
            url = "https://google.com",
            source = "Header news",
            saved = true,
        ),
        newsCount: Int = 2,
    ): NewsUiState {
        return NewsUiState.Loaded(
            header = header,
            news = (0 until newsCount).map {
                NewsUiItem(
                    headline = "Breaking news ${it + 1}",
                    subhead = null,
                    imageUrl = "https://images.google.com",
                    author = "Segun news ${it + 1}",
                    date = "Yesterday",
                    url = "https://google.com",
                    source = "Header news",
                    saved = false,
                )
            }
        )
    }
}