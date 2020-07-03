package com.segunfamisa.zeitung.ui.news

import androidx.lifecycle.ViewModel
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase
): ViewModel() {


}
