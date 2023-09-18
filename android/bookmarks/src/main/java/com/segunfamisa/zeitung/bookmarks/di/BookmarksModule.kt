package com.segunfamisa.zeitung.bookmarks.di

import androidx.lifecycle.ViewModel
import com.segunfamisa.zeitung.bookmarks.BookmarksViewModel
import com.segunfamisa.zeitung.common.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BookmarksModule {

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel::class)
    abstract fun bindBookmarksViewModel(vm: BookmarksViewModel): ViewModel
}
