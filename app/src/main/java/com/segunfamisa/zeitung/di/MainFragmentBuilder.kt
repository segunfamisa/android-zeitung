package com.segunfamisa.zeitung.di

import com.segunfamisa.zeitung.ui.news.NewsFragment
import com.segunfamisa.zeitung.ui.news.NewsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector(modules = [NewsModule::class])
    abstract fun contributeNewsFragment(): NewsFragment
}