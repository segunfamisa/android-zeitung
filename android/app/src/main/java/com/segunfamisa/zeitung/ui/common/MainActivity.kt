package com.segunfamisa.zeitung.ui.common

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelLazy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.bookmarks.bookmarksGraph
import com.segunfamisa.zeitung.common.AppState
import com.segunfamisa.zeitung.common.LocalAppState
import com.segunfamisa.zeitung.common.di.ViewModelFactory
import com.segunfamisa.zeitung.common.rememberAppState
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.news.newsNavGraph
import com.segunfamisa.zeitung.onboarding.OnboardingContent
import com.segunfamisa.zeitung.onboarding.OnboardingViewModel
import com.segunfamisa.zeitung.sources.sourcesNavGraph
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
                Main(appState)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @Composable
    private fun Main(appState: AppState) {
        val navController = rememberNavController()
        val backStack by navController.currentBackStackEntryFlow.collectAsState(navController.currentBackStackEntry)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = when (backStack?.destination?.route) {
                                Routes.Sources -> stringResource(R.string.app_bar_sources)
                                Routes.Bookmarks -> stringResource(id = R.string.app_bar_bookmarks)
                                Routes.News -> stringResource(id = R.string.app_bar_news)
                                else -> stringResource(R.string.app_name)
                            }
                        )
                    }
                )
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (appState.shouldUseBottomBar) {
                    BottomNavBar(
                        navController = navController,
                        items = listOf(NavItem.News, NavItem.Bookmarks, NavItem.Sources)
                    )
                }
            },
        ) { padding ->
            Row {
                if (appState.shouldUseNavRail) {
                    NavRail(
                        navController = navController,
                        items = listOf(NavItem.News, NavItem.Bookmarks, NavItem.Sources),
                        modifier = Modifier.safeDrawingPadding()
                    )
                }
                NavHost(
                    navController = navController,
                    startDestination = Routes.News,
                    modifier = Modifier
                        .padding(padding)
                        .consumedWindowInsets(padding)
                ) {
                    newsNavGraph(route = Routes.News, vmFactory = vmFactory)
                    sourcesNavGraph(route = Routes.Sources, vmFactory = vmFactory)
                    bookmarksGraph(Routes.Bookmarks, vmFactory = vmFactory)
                }
            }
        }
    }

}
