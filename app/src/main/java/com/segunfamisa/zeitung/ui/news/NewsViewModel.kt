package com.segunfamisa.zeitung.ui.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import com.segunfamisa.zeitung.domain.headlines.HeadlineQueryParam
import com.segunfamisa.zeitung.util.DispatcherProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    fun fetchHeadlines() {
        viewModelScope.launch(dispatcherProvider.io) {
            val params = HeadlineQueryParam(category = "technology", country = "de")
            val headlines = getHeadlinesUseCase.invoke(params)
            headlines.fold({
                Log.e(LOG_TAG, it.message)
            }, {
                Log.d(LOG_TAG, it.toString())
                Unit
            })
        }
    }

    private companion object {
        const val LOG_TAG = "NewsViewModel"
    }
}
