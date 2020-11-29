package com.segunfamisa.zeitung.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelLazy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.common.theme.ZeitungTheme
import com.segunfamisa.zeitung.news.NewsContent
import com.segunfamisa.zeitung.news.NewsViewModel
import com.segunfamisa.zeitung.onboarding.OnboardingContent
import com.segunfamisa.zeitung.onboarding.OnboardingViewModel
import com.segunfamisa.zeitung.util.viewmodel.ViewModelFactory
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZeitungTheme {
                App()
            }
        }
    }

    @Composable
    private fun App() {
        val onboardingNavController = rememberNavController()
        NavHost(navController = onboardingNavController, startDestination = Routes.Onboarding) {
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

    @Composable
    private fun Main() {
        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
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
            }
        ) {
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
                    LaunchedEffect(Routes.News) {
                        newsViewModelLazy.value.fetchHeadlines()
                    }
                }
            }
        }
    }

}
