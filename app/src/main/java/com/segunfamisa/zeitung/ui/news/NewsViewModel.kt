package com.segunfamisa.zeitung.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import com.segunfamisa.zeitung.domain.headlines.HeadlineQueryParam
import com.segunfamisa.zeitung.ui.UiState
import com.segunfamisa.zeitung.util.DispatcherProvider
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
            val headlines = getHeadlinesUseCase.invoke(params)
            headlines.fold({
                Log.e(LOG_TAG, it.toString())
                _state.postValue(UiState.Error(error = Exception(it.message)))
            }, {
                _state.postValue(UiState.Success(data = it.toUiNewsItemList()))
            })
        }
    }

    private fun List<Article>.toUiNewsItemList(): List<UiNewsItem> {
        return this.map {
            UiNewsItem(
                headline = it.title,
                subhead = it.description,
                date = it.publishedAt,
                author = it.author,
                image = null, // TODO implement converting url to image asset
                url = it.url,
                isSaved = false, // TODO determine this with bookmarks list.
                source = UiSourceItem(
                    id = it.source.id,
                    logo = null, // TODO implement converting url to image asset
                    name = it.source.name
                )
            )
        }
    }

    private companion object {
        const val LOG_TAG = "NewsViewModel"
    }
}
