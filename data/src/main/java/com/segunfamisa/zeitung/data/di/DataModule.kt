package com.segunfamisa.zeitung.data.di

import com.segunfamisa.zeitung.data.common.ErrorParser
import com.segunfamisa.zeitung.data.common.ErrorParserImpl
import com.segunfamisa.zeitung.data.di.qualifiers.DataSource
import com.segunfamisa.zeitung.data.headlines.HeadlinesRepositoryImpl
import com.segunfamisa.zeitung.data.headlines.HeadlinesSource
import com.segunfamisa.zeitung.data.headlines.RemoteHeadlinesSource
import com.segunfamisa.zeitung.data.news.NewsRepositoryImpl
import com.segunfamisa.zeitung.data.news.NewsSource
import com.segunfamisa.zeitung.data.news.RemoteNewsSource
import com.segunfamisa.zeitung.data.newssources.NewsSourcesDataSource
import com.segunfamisa.zeitung.data.newssources.NewsSourcesRepositoryImpl
import com.segunfamisa.zeitung.data.newssources.RemoteNewsSourcesDataSource
import com.segunfamisa.zeitung.data.remote.ApiKeyProvider
import com.segunfamisa.zeitung.data.remote.ApiKeyProviderImpl
import com.segunfamisa.zeitung.data.remote.ApiService
import com.segunfamisa.zeitung.data.remote.ApiServiceCreator
import com.segunfamisa.zeitung.data.remote.UrlProvider
import com.segunfamisa.zeitung.data.remote.UrlProviderImpl
import com.segunfamisa.zeitung.domain.headlines.HeadlinesRepository
import com.segunfamisa.zeitung.domain.news.NewsRepository
import com.segunfamisa.zeitung.domain.newssources.NewsSourcesRepository
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DataModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        internal fun provideApiService(apiServiceCreator: ApiServiceCreator): ApiService {
            return apiServiceCreator.createService()
        }

        @Provides
        @JvmStatic
        internal fun provideMoshi(): Moshi {
            return Moshi.Builder().build()
        }
    }

    @Binds
    internal abstract fun bindErrorParser(errorParserImpl: ErrorParserImpl): ErrorParser

    // region Remote data

    @Binds
    internal abstract fun bindHeadlineRepository(headlineRepository: HeadlinesRepositoryImpl): HeadlinesRepository

    @Binds
    internal abstract fun bindApiKeyProvider(apiKeyProvider: ApiKeyProviderImpl): ApiKeyProvider

    @Binds
    internal abstract fun bindUrlProvider(urlProvider: UrlProviderImpl): UrlProvider

    @Binds
    @DataSource(type = "remote")
    internal abstract fun bindRemoteHeadlineSource(remoteSource: RemoteHeadlinesSource): HeadlinesSource

    @Binds
    internal abstract fun bindNewsSourceRepository(newsSourcesRepository: NewsSourcesRepositoryImpl): NewsSourcesRepository

    @Binds
    @DataSource(type = "remote")
    internal abstract fun bindRemoteNewsSourceDataSource(remoteDataSource: RemoteNewsSourcesDataSource): NewsSourcesDataSource

    @Binds
    @DataSource(type = "remote")
    internal abstract fun bindRemoteNewsSource(remoteDataSource: RemoteNewsSource): NewsSource

    @Binds
    internal abstract fun bindNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    // endregion
}
