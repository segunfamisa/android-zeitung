package com.segunfamisa.zeitung.sources.di

import androidx.lifecycle.ViewModel
import com.segunfamisa.zeitung.common.di.ViewModelKey
import com.segunfamisa.zeitung.sources.SourcesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SourcesModule {

    @Binds
    @IntoMap
    @ViewModelKey(SourcesViewModel::class)
    internal abstract fun bindSourcesViewModel(sourcesViewModel: SourcesViewModel): ViewModel
}
