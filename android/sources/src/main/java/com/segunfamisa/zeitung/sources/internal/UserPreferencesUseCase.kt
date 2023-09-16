package com.segunfamisa.zeitung.sources.internal

import com.segunfamisa.zeitung.core.entities.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UserPreferencesUseCase @Inject constructor() {

    private val _followedSources: MutableStateFlow<List<Source>> = MutableStateFlow(emptyList())
    val followedSources: Flow<List<Source>> = _followedSources

    val language: Flow<String> = flowOf("en")

}
