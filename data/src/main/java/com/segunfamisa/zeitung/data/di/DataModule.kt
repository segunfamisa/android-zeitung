package com.segunfamisa.zeitung.data.di

import com.segunfamisa.zeitung.data.di.qualifiers.DataSource
import com.segunfamisa.zeitung.data.headlines.HeadlinesRepositoryImpl
import com.segunfamisa.zeitung.data.headlines.HeadlinesSource
import com.segunfamisa.zeitung.data.headlines.RemoteHeadlinesSource
import com.segunfamisa.zeitung.data.sources.remote.ApiKeyProvider
import com.segunfamisa.zeitung.data.sources.remote.ApiKeyProviderImpl
import com.segunfamisa.zeitung.data.sources.remote.ApiService
import com.segunfamisa.zeitung.data.sources.remote.ApiServiceCreator
import com.segunfamisa.zeitung.data.sources.remote.UrlProvider
import com.segunfamisa.zeitung.data.sources.remote.UrlProviderImpl
import com.segunfamisa.zeitung.domain.headlines.HeadlinesRepository
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
    }

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

    // endregion
}
