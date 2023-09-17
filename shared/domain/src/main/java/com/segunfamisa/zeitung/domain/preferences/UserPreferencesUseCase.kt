package com.segunfamisa.zeitung.domain.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesUseCase @Inject constructor() {
    private val _followedSourcesIds: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val followedSourceIds: Flow<List<String>> = _followedSourcesIds

    val language: Flow<String> = flowOf("en")

    fun toggleSourceFollowing(sourceId: String, followed: Boolean) {
        _followedSourcesIds.update {
            if (followed) {
                it + listOf(sourceId)
            } else {
                it.toMutableList().apply {
                    remove(sourceId)
                }
            }
        }
    }
}
