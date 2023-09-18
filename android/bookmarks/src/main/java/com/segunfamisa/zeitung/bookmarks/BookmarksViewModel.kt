package com.segunfamisa.zeitung.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.preferences.UserPreferencesUseCase
import com.segunfamisa.zeitung.news.ui.NewsUiState
import com.segunfamisa.zeitung.news.ui.UiItemMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class BookmarksViewModel @Inject constructor(
    private val uiItemMapper: UiItemMapper,
    private val userPreferencesUseCase: UserPreferencesUseCase
) : ViewModel() {

    private var savedArticles: List<Article> = emptyList()

    val uiState: StateFlow<NewsUiState> = userPreferencesUseCase.savedArticles
        .map { articles ->
            savedArticles = articles

            NewsUiState.Loaded(
                header = null,
                news = articles.map { article ->
                    uiItemMapper.createUiItem(
                        article = article,
                        savedChecker = { true }
                    )
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = NewsUiState.Loading
        )

    fun removeArticle(url: String) {
        viewModelScope.launch {
            savedArticles.find { it.url == url }
                ?.let {
                    userPreferencesUseCase.toggleSavedArticle(article = it, saved = false)
                }
        }
    }
}
