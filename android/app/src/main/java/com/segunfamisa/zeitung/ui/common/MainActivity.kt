package com.segunfamisa.zeitung.ui.common

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelLazy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.news.NewsContent
import com.segunfamisa.zeitung.news.NewsViewModel
import com.segunfamisa.zeitung.onboarding.OnboardingContent
import com.segunfamisa.zeitung.onboarding.OnboardingViewModel
import com.segunfamisa.zeitung.util.viewmodel.ViewModelFactory
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var newsViewModelFactory: ViewModelFactory<NewsViewModel>

    @Inject
    lateinit var onboardingViewModelFactory: ViewModelFactory<OnboardingViewModel>

    private val newsViewModelLazy: Lazy<NewsViewModel> = ViewModelLazy(
        viewModelClass = NewsViewModel::class,
        storeProducer = { viewModelStore },
        factoryProducer = { newsViewModelFactory }
    )

    private val onboardingViewModelLazy: Lazy<OnboardingViewModel> = ViewModelLazy(
        viewModelClass = OnboardingViewModel::class,
        storeProducer = { viewModelStore },
        factoryProducer = { onboardingViewModelFactory }
    )

    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZeitungTheme {
                App()
            }
        }
    }

    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @FlowPreview
    @Composable
    private fun App() {
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
                Main()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @Composable
    private fun Main() {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) }
                )
            },
            bottomBar = {
                BottomNavBar(
                    navController = navController,
                    items = listOf(NavItem.News, NavItem.Explore, NavItem.Bookmarks)
                )
            },
        ) {
            LaunchedEffect(Routes.News) {
                newsViewModelLazy.value.fetchHeadlines()
            }
            NavHost(navController = navController, startDestination = Routes.News) {
                composable(Routes.Explore) {
                    Text(text = "Explore")
                }
                composable(Routes.Bookmarks) {
                    Text(text = "Bookmarks")
                }
                composable(Routes.News) {
                    NewsContent(
                        newsViewModel = newsViewModelLazy,
                        onItemClicked = {
                            // Handle click listener
                        }
                    )
                }
            }
        }
    }

}
