package com.segunfamisa.zeitung.bookmarks

import androidx.lifecycle.ViewModel
import com.segunfamisa.zeitung.domain.preferences.UserPreferencesUseCase
import javax.inject.Inject

internal class BookmarksViewModel @Inject constructor(
    private val userPreferencesUseCase: UserPreferencesUseCase,
) : ViewModel() {


}
