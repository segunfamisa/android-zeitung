package com.segunfamisa.zeitung.data.remote.di

import com.segunfamisa.zeitung.data.di.qualifiers.Remote
import com.segunfamisa.zeitung.data.headlines.HeadlinesSource
import com.segunfamisa.zeitung.data.news.NewsSource
import com.segunfamisa.zeitung.data.newssources.NewsSourcesDataSource
import com.segunfamisa.zeitung.data.remote.service.UrlProviderImpl
import com.segunfamisa.zeitung.data.remote.service.ApiKeyProvider
import com.segunfamisa.zeitung.data.remote.service.ApiKeyProviderImpl
import com.segunfamisa.zeitung.data.remote.service.ApiService
import com.segunfamisa.zeitung.data.remote.service.ApiServiceCreator
import com.segunfamisa.zeitung.data.remote.service.UrlProvider
import com.segunfamisa.zeitung.data.remote.common.ErrorParser
import com.segunfamisa.zeitung.data.remote.common.ErrorParserImpl
import com.segunfamisa.zeitung.data.remote.headlines.RemoteHeadlinesSource
import com.segunfamisa.zeitung.data.remote.news.RemoteNewsSource
import com.segunfamisa.zeitung.data.remote.newssources.RemoteNewsSourcesDataSource
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteDataModule {

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

    @Binds
    internal abstract fun bindApiKeyProvider(apiKeyProvider: ApiKeyProviderImpl): ApiKeyProvider

    @Binds
    internal abstract fun bindUrlProvider(urlProvider: UrlProviderImpl): UrlProvider

    @Binds
    @Remote
    internal abstract fun bindRemoteHeadlineSource(remoteSource: RemoteHeadlinesSource): HeadlinesSource

    @Binds
    @Remote
    internal abstract fun bindRemoteNewsSourceDataSource(remoteDataSource: RemoteNewsSourcesDataSource): NewsSourcesDataSource

    @Binds
    @Remote
    internal abstract fun bindRemoteNewsSource(remoteDataSource: RemoteNewsSource): NewsSource
}