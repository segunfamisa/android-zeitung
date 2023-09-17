package com.segunfamisa.zeitung.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import com.segunfamisa.zeitung.domain.headlines.HeadlineQueryParam
import com.segunfamisa.zeitung.domain.preferences.UserPreferencesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val uiItemMapper: UiItemMapper,
    private val userPreferencesUseCase: UserPreferencesUseCase
) : ViewModel() {

    private var headlines: List<Article> = emptyList()

    val uiState: StateFlow<NewsUiState> = userPreferencesUseCase.followedSourceIds
        .flatMapConcat { sources ->
            combine(
                userPreferencesUseCase.savedArticles,
                getHeadlinesUseCase.execute(param = HeadlineQueryParam(sources = sources))
            ) { savedArticles, eitherHeadlines ->
                eitherHeadlines.fold(
                    ifLeft = { error ->
                        NewsUiState.Error(message = error.message)
                    },
                    ifRight = { articles ->
                        // save the headlines
                        headlines = articles

                        val hasHeader = articles.isNotEmpty()
                        NewsUiState.Loaded(
                            header = articles.firstOrNull()?.let { article ->
                                uiItemMapper.createUiItem(
                                    article = article,
                                    savedChecker = { toSave ->
                                        savedArticles.any { toSave.url == it.url }
                                    })
                            },
                            news = if (hasHeader) {
                                articles.subList(1, articles.size)
                            } else {
                                articles
                            }.map { article ->
                                uiItemMapper.createUiItem(
                                    article = article,
                                    savedChecker = { toSave ->
                                        savedArticles.any { toSave.url == it.url }
                                    })
                            }
                        )
                    }
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = NewsUiState.Loading
        )

    fun saveNewsItem(url: String, saved: Boolean) {
        viewModelScope.launch {
            val article = headlines.find { it.url == url }
            article?.let {
                userPreferencesUseCase.toggleSavedArticle(article = it, saved = saved)
            }
        }
    }
}
