package com.segunfamisa.zeitung.di

import com.segunfamisa.zeitung.ui.common.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainFragmentBuilder::class])
    abstract fun contributeMainActivity(): MainActivity
}
