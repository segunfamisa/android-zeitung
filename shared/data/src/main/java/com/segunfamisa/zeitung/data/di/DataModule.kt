package com.segunfamisa.zeitung.data.di

import com.segunfamisa.zeitung.data.headlines.HeadlinesRepositoryImpl
import com.segunfamisa.zeitung.data.news.NewsRepositoryImpl
import com.segunfamisa.zeitung.data.newssources.NewsSourcesRepositoryImpl
import com.segunfamisa.zeitung.domain.getnews.NewsRepository
import com.segunfamisa.zeitung.domain.headlines.HeadlinesRepository
import com.segunfamisa.zeitung.domain.newssources.NewsSourcesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    // region data

    @Binds
    internal abstract fun bindHeadlineRepository(headlineRepository: HeadlinesRepositoryImpl): HeadlinesRepository

    @Binds
    internal abstract fun bindNewsSourceRepository(newsSourcesRepository: NewsSourcesRepositoryImpl): NewsSourcesRepository

    @Binds
    internal abstract fun bindNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    // endregion
}
