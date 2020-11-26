package com.segunfamisa.zeitung.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.headlines.HeadlineQueryParam
import com.segunfamisa.zeitung.common.UiState
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import com.segunfamisa.zeitung.utils.DispatcherProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state: MutableLiveData<UiState<List<UiNewsItem>>> = MutableLiveData()
    val state: LiveData<UiState<List<UiNewsItem>>>
        get() = _state

    fun fetchHeadlines() {
        viewModelScope.launch(dispatcherProvider.io) {
            val params = HeadlineQueryParam(category = "technology", country = "us")
            _state.postValue(UiState.Loading)
            getHeadlinesUseCase.execute(params)
                .collect { headlines ->
                    headlines.fold({
                        Log.e(LOG_TAG, it.toString())
                        _state.postValue(UiState.Error(error = Exception(it.message)))
                    }, {
                        _state.postValue(UiState.Success(data = it.toUiNewsItemList()))
                    })
                }
        }
    }

    private fun List<Article>.toUiNewsItemList(): List<UiNewsItem> {
        return this.mapIndexed { index, article ->
            if (index == 0) {
                UiNewsItem.Top(
                    headline = article.title,
                    subhead = article.description,
                    date = article.publishedAt,
                    author = article.author,
                    image = null,
                    imageUrl = article.imageUrl,
                    url = article.url,
                    isSaved = article.isSaved,
                    source = UiSourceItem(
                        id = article.source.id,
                        logo = null, // TODO implement converting url to image asset
                        name = article.source.name
                    )
                )
            } else {
                UiNewsItem.Regular(
                    headline = article.title,
                    subhead = article.description,
                    date = article.publishedAt,
                    author = article.author,
                    image = null,
                    imageUrl = article.imageUrl,
                    url = article.url,
                    isSaved = article.isSaved,
                    source = UiSourceItem(
                        id = article.source.id,
                        logo = null, // TODO implement converting url to image asset
                        name = article.source.name
                    )
                )
            }
        }
    }

    fun saveNewsItem(newsItem: UiNewsItem, shouldSave: Boolean) {
        // TODO("Not yet implemented")
    }

    private companion object {
        const val LOG_TAG = "NewsViewModel"
    }
}
