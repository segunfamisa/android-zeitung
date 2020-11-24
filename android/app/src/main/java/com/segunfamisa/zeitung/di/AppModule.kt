package com.segunfamisa.zeitung.di

import com.segunfamisa.zeitung.data.di.DataModule
import com.segunfamisa.zeitung.utils.DefaultDispatcherProvider
import com.segunfamisa.zeitung.utils.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module(includes = [DataModule::class])
abstract class AppModule {

    @Binds
    abstract fun bindDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider
}
