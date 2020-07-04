package com.segunfamisa.zeitung.ui.news

import com.segunfamisa.zeitung.di.NewsContainer
import dagger.Binds
import dagger.Module

@Module
abstract class NewsModule {

    @Binds
    abstract fun bindNewsContainer(newsContainerImpl: NewsContainerImpl): NewsContainer
}
