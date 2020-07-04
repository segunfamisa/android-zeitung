package com.segunfamisa.zeitung.ui.news

import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelStore
import com.segunfamisa.zeitung.di.NewsContainer
import com.segunfamisa.zeitung.util.viewmodel.ViewModelFactory
import javax.inject.Inject

class NewsContainerImpl @Inject constructor(
    private val viewModelStore: ViewModelStore,
    private val viewModelFactory: ViewModelFactory<NewsViewModel>
) : NewsContainer {

    override val newsViewModel: Lazy<NewsViewModel>
        get() {
            return ViewModelLazy(
                viewModelClass = NewsViewModel::class,
                storeProducer = { viewModelStore },
                factoryProducer = { viewModelFactory }
            )
        }

}
