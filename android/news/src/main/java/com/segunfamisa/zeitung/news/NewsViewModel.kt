package com.segunfamisa.zeitung.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import com.segunfamisa.zeitung.domain.headlines.HeadlineQueryParam
import com.segunfamisa.zeitung.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val uiItemMapper: UiItemMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(value = NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState>
        get() = _uiState

    fun fetchHeadlines() {
        viewModelScope.launch(dispatcherProvider.io) {
            val params = HeadlineQueryParam(category = "technology", country = "us")
            _uiState.value = NewsUiState.Loading
            getHeadlinesUseCase.execute(params)
                .collect { headlines ->
                    headlines.fold({
                        Log.e(LOG_TAG, it.toString())
                        _uiState.value = NewsUiState.Error(message = it.message)
                    }, { articles ->
                        val hasHeader = articles.isNotEmpty()
                        _uiState.value = NewsUiState.Loaded(
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
                    })
                }
        }
    }

    fun saveNewsItem(url: String, shouldSave: Boolean) {
        // TODO("Not yet implemented")
    }

    private companion object {
        const val LOG_TAG = "NewsViewModel"
    }
}
