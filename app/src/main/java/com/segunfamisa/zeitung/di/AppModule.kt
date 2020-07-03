package com.segunfamisa.zeitung.di

import com.segunfamisa.zeitung.data.di.DataModule
import com.segunfamisa.zeitung.ui.news.NewsModule
import dagger.Binds
import dagger.Module

@Module(includes = [DataModule::class, NewsModule::class])
abstract class AppModule {

    @Binds
    abstract fun bindAppContainer(appContainerImpl: AppContainerImpl): AppContainer
}
