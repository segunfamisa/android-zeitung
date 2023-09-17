package com.segunfamisa.zeitung.domain.preferences

import com.segunfamisa.zeitung.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val dispatcherProvider: DispatcherProvider,
) {
    val followedSourceIds: Flow<List<String>> = userPreferencesRepository.savedSources

    val language: Flow<String> = userPreferencesRepository.language

    suspend fun toggleSourceFollowing(sourceId: String, followed: Boolean) {
        withContext(dispatcherProvider.io) {
            userPreferencesRepository.saveSource(sourceId, followed)
        }
    }
}
