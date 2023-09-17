package com.segunfamisa.zeitung.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import com.segunfamisa.zeitung.domain.headlines.HeadlineQueryParam
import com.segunfamisa.zeitung.domain.preferences.UserPreferencesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(FlowPreview::class)
class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val uiItemMapper: UiItemMapper,
    private val userPreferencesUseCase: UserPreferencesUseCase
) : ViewModel() {

    val uiState: StateFlow<NewsUiState> = userPreferencesUseCase.followedSourceIds
        .flatMapConcat { sources ->
            getHeadlinesUseCase.execute(param = HeadlineQueryParam(sources = sources))
        }
        .map { eitherHeadlines ->
            eitherHeadlines.fold(
                ifLeft = { error ->
                    NewsUiState.Error(message = error.message)
                },
                ifRight = { articles ->
                    val hasHeader = articles.isNotEmpty()
                    NewsUiState.Loaded(
                        header = articles.firstOrNull()?.let {
                            uiItemMapper.createUiItem(it)
                        },
                        news = if (hasHeader) {
                            articles.subList(1, articles.size)
                        } else {
                            articles
                        }.map {
                            uiItemMapper.createUiItem(it)
                        }
                    )
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = NewsUiState.Loading
        )

    fun saveNewsItem(url: String, saved: Boolean) {
        // TODO("Not yet implemented")
    }
}
