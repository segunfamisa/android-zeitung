package com.segunfamisa.zeitung.ui.common

import androidx.lifecycle.ViewModelStore
import com.segunfamisa.zeitung.di.AppContainer
import com.segunfamisa.zeitung.di.AppContainerImpl
import com.segunfamisa.zeitung.ui.news.NewsModule
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [NewsModule::class])
abstract class MainActivityModule {

    companion object {

        @Provides
        fun provideViewModelStore(mainActivity: MainActivity): ViewModelStore {
            return mainActivity.viewModelStore
        }
    }

    @Binds
    abstract fun bindAppContainer(appContainerImpl: AppContainerImpl): AppContainer
}
