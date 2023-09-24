package com.segunfamisa.zeitung.ui.common

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelLazy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.common.AppState
import com.segunfamisa.zeitung.common.LocalAppState
import com.segunfamisa.zeitung.common.di.ViewModelFactory
import com.segunfamisa.zeitung.common.rememberAppState
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.onboarding.OnboardingContent
import com.segunfamisa.zeitung.onboarding.OnboardingViewModel
import com.segunfamisa.zeitung.ui.common.nav.Routes
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalLayoutApi::class)
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val onboardingViewModelLazy: Lazy<OnboardingViewModel> = ViewModelLazy(
        viewModelClass = OnboardingViewModel::class,
        storeProducer = { viewModelStore },
        factoryProducer = { vmFactory }
    )

    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZeitungTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                val appState = rememberAppState(windowSizeClass = windowSizeClass)
                CompositionLocalProvider(LocalAppState provides appState) {
                    App(appState)
                }
            }
        }
    }

    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @FlowPreview
    @Composable
    private fun App(appState: AppState) {
        val onboardingNavController = rememberNavController()
        NavHost(navController = onboardingNavController, startDestination = Routes.Main) {
            composable(Routes.Onboarding) {
                OnboardingContent(
                    onboardingViewModel = onboardingViewModelLazy,
                    onContinue = {
                        onboardingViewModelLazy.value.onContinueClicked(token = it)
                    },
                    onApiTokenChange = {
                        onboardingViewModelLazy.value.onApiTokenChange(token = it)
                    },
                )
                val observeState = onboardingViewModelLazy.value.continueToApp.observeAsState()
                if (observeState.value == true) {
                    onboardingNavController.navigate(route = Routes.Main)
                }
            }
            composable(Routes.Main) {
                MainApp(appState = appState, vmFactory = vmFactory)
            }
        }
    }

}
