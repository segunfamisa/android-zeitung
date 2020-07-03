package com.segunfamisa.zeitung.di

import com.segunfamisa.zeitung.ui.news.NewsViewModel

/**
 * Container to help get DI objects - idea is from JetNews app
 */
interface AppContainer {

    fun newsContainer(): NewsContainer
}

/**
 * Container to get News screen dependencies
 */
interface NewsContainer {
    val newsViewModel: Lazy<NewsViewModel>
}
