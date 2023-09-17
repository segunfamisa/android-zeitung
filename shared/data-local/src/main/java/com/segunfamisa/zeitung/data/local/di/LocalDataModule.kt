package com.segunfamisa.zeitung.data.local.di

import com.segunfamisa.zeitung.data.local.preferences.UserPreferencesSourceImpl
import com.segunfamisa.zeitung.data.preferences.UserPreferencesSource
import dagger.Binds
import dagger.Module

@Module
abstract class LocalDataModule {

    @Binds
    internal abstract fun bindUserPreferencesSource(impl: UserPreferencesSourceImpl): UserPreferencesSource
}
