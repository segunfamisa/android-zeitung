package com.segunfamisa.zeitung.data.di

import com.segunfamisa.zeitung.data.credentials.ApiCredentialsRepositoryImpl
import com.segunfamisa.zeitung.data.headlines.HeadlinesRepositoryImpl
import com.segunfamisa.zeitung.data.news.NewsRepositoryImpl
import com.segunfamisa.zeitung.data.newssources.NewsSourcesRepositoryImpl
import com.segunfamisa.zeitung.data.preferences.UserPreferencesRepositoryImpl
import com.segunfamisa.zeitung.domain.credentials.ApiCredentialsRepository
import com.segunfamisa.zeitung.domain.getnews.NewsRepository
import com.segunfamisa.zeitung.domain.headlines.HeadlinesRepository
import com.segunfamisa.zeitung.domain.newssources.NewsSourcesRepository
import com.segunfamisa.zeitung.domain.preferences.UserPreferencesRepository
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

    @Binds
    internal abstract fun bindApiCredentialsRepository(apiCredentialsRepository: ApiCredentialsRepositoryImpl): ApiCredentialsRepository

    @Binds
    internal abstract fun bindUserPreferencesRepository(impl: UserPreferencesRepositoryImpl): UserPreferencesRepository

    // endregion
}
