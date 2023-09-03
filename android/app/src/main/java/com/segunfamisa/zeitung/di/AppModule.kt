package com.segunfamisa.zeitung.di

import com.segunfamisa.zeitung.data.ApiKeyProviderImpl
import com.segunfamisa.zeitung.data.UrlProviderImpl
import com.segunfamisa.zeitung.data.di.DataModule
import com.segunfamisa.zeitung.data.remote.di.RemoteDataModule
import com.segunfamisa.zeitung.data.remote.service.ApiKeyProvider
import com.segunfamisa.zeitung.data.remote.service.UrlProvider
import com.segunfamisa.zeitung.utils.DefaultDispatcherProvider
import com.segunfamisa.zeitung.utils.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        DataModule::class,
        RemoteDataModule::class
    ]
)
abstract class AppModule {

    @Binds
    abstract fun bindDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider

    @Binds
    internal abstract fun bindApiKeyProvider(apiKeyProvider: ApiKeyProviderImpl): ApiKeyProvider

    @Binds
    internal abstract fun bindUrlProvider(urlProvider: UrlProviderImpl): UrlProvider
}
