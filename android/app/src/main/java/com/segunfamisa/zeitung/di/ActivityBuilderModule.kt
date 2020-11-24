package com.segunfamisa.zeitung.di

import com.segunfamisa.zeitung.ui.common.MainActivity
import com.segunfamisa.zeitung.ui.common.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity
}