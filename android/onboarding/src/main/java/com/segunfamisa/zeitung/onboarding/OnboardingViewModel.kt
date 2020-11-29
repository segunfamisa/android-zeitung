package com.segunfamisa.zeitung.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.segunfamisa.zeitung.domain.credentials.SaveApiCredentialsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val saveApiCredentialsUseCase: SaveApiCredentialsUseCase,
) : ViewModel() {

    private val _continueButtonEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val continueButtonEnabled: LiveData<Boolean>
        get() = _continueButtonEnabled

    private val _continueToApp: MutableLiveData<Boolean> = MutableLiveData()
    val continueToApp: LiveData<Boolean>
        get() = _continueToApp

    fun onApiTokenChange(token: String) {
        viewModelScope.launch {
            flowOf(token)
                .debounce(300)
                .collect {
                    _continueButtonEnabled.value = it.isNotEmpty()
                }
        }
    }

    fun onContinueClicked(token: String) {
        viewModelScope.launch {
            saveApiCredentialsUseCase.execute(param = token)
                .collect {
                    when (it) {
                        is Either.Right -> _continueToApp.value = true
                        else -> Unit
                    }
                }
        }
    }

}
