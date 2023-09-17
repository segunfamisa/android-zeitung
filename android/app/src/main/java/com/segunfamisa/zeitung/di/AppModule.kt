package com.segunfamisa.zeitung.di

import android.app.Application
import android.content.Context
import com.segunfamisa.zeitung.data.ApiKeyProviderImpl
import com.segunfamisa.zeitung.data.UrlProviderImpl
import com.segunfamisa.zeitung.data.di.DataModule
import com.segunfamisa.zeitung.data.local.di.LocalDataModule
import com.segunfamisa.zeitung.data.remote.di.RemoteDataModule
import com.segunfamisa.zeitung.data.remote.service.ApiKeyProvider
import com.segunfamisa.zeitung.data.remote.service.UrlProvider
import com.segunfamisa.zeitung.news.NewsModule
import com.segunfamisa.zeitung.sources.di.SourcesModule
import com.segunfamisa.zeitung.utils.DefaultDispatcherProvider
import com.segunfamisa.zeitung.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        DataModule::class,
        RemoteDataModule::class,
        LocalDataModule::class,
        NewsModule::class,
        SourcesModule::class,
    ]
)
abstract class AppModule {

    companion object {

        @Provides
        fun provideAppContext(app: Application): Context {
            return app.applicationContext
        }
    }

    @Binds
    abstract fun bindDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider

    @Binds
    internal abstract fun bindApiKeyProvider(apiKeyProvider: ApiKeyProviderImpl): ApiKeyProvider

    @Binds
    internal abstract fun bindUrlProvider(urlProvider: UrlProviderImpl): UrlProvider
}
