package com.segunfamisa.zeitung.onboarding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    Log.d("Onboarding", "API token saved!")
                }
        }
    }

}