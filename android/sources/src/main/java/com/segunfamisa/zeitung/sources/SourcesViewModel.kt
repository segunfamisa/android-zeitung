package com.segunfamisa.zeitung.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfamisa.zeitung.domain.newssources.GetNewsSourcesUseCase
import com.segunfamisa.zeitung.domain.newssources.SourcesQueryParam
import com.segunfamisa.zeitung.domain.preferences.UserPreferencesUseCase
import com.segunfamisa.zeitung.sources.internal.UiSourceMapper
import com.segunfamisa.zeitung.sources.internal.UiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(FlowPreview::class)
internal class SourcesViewModel @Inject constructor(
    private val getNewsSourcesUseCase: GetNewsSourcesUseCase,
    private val userPreferencesUseCase: UserPreferencesUseCase,
    private val uiSourceMapper: UiSourceMapper,
) : ViewModel() {

    val uiState: StateFlow<UiState> = userPreferencesUseCase.language
        .flatMapConcat { language ->
            combine(
                getNewsSourcesUseCase.execute(param = SourcesQueryParam(language = language)),
                userPreferencesUseCase.followedSourceIds,
            ) { eitherNewsSources, followedSources ->
                eitherNewsSources.fold(
                    ifLeft = { error ->
                        UiState(loading = false, error = error.message)
                    },
                    ifRight = { sources ->
                        UiState(
                            loading = false,
                            sources = uiSourceMapper.from(
                                input = sources,
                                followedChecker = { sourceId ->
                                    followedSources.any { it == sourceId }
                                }
                            )
                        )
                    }
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = UiState(loading = true),
            started = SharingStarted.WhileSubscribed()
        )
}
