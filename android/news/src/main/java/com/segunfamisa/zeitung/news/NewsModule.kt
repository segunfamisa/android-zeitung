package com.segunfamisa.zeitung.news

import androidx.lifecycle.ViewModel
import com.segunfamisa.zeitung.common.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    internal abstract fun bindsNewsViewModel(viewModel: NewsViewModel): ViewModel
}
