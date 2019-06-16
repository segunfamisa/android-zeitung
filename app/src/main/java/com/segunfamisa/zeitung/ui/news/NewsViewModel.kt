package com.segunfamisa.zeitung.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import com.segunfamisa.zeitung.domain.headlines.HeadlineQueryParam
import com.segunfamisa.zeitung.util.DateFormatter
import com.segunfamisa.zeitung.util.viewmodel.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _articles: MutableLiveData<List<UiArticleItem>> = MutableLiveData()
    val articles: LiveData<List<UiArticleItem>>
        get() = _articles

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _viewArticleEvent: MutableLiveData<Event<String>> = MutableLiveData()
    val viewArticleEvent: LiveData<Event<String>>
        get() = _viewArticleEvent

    fun openArticle(article: UiArticleItem) {
        _viewArticleEvent.postValue(Event(article.url))
    }

    fun getHeadlines() {
        _loading.postValue(true)
        viewModelScope.launch {
            val response =
                getHeadlinesUseCase.invoke(param = HeadlineQueryParam(country = "us", category = "technology"))
            response.fold({ error ->
                _loading.postValue(false)
                _error.postValue(error.message)
            }, { articles ->
                _loading.postValue(false)
                _articles.postValue(mapToUiArticles(articles))
            })
        }
    }

    private fun mapToUiArticles(articles: List<Article>): List<UiArticleItem> =
        articles.map {
            UiArticleItem(
                source = it.source.name,
                title = it.title,
                imageUrl = it.imageUrl,
                description = it.description,
                date = dateFormatter.format(date = it.publishedAt),
                url = it.url
            )
        }
}
