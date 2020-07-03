package com.segunfamisa.zeitung.ui.news

import com.segunfamisa.zeitung.di.NewsContainer
import dagger.Lazy
import javax.inject.Inject

class NewsContainerImpl @Inject constructor(
    private val newsViewModelProvider: Lazy<NewsViewModel>
) : NewsContainer {

    override val newsViewModel: NewsViewModel
        get() = newsViewModelProvider.get()

}
