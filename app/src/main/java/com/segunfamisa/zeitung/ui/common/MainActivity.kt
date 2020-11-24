package com.segunfamisa.zeitung.ui.common

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.di.AppContainer
import com.segunfamisa.zeitung.ui.news.NewsContent
import com.segunfamisa.zeitung.ui.theme.ZeitungTheme
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZeitungTheme {
                App(
                    appContainer = appContainer
                )
            }
        }
    }

    @Composable
    private fun App(appContainer: AppContainer) {
        val navBarState = NavBarState(listOf(NavItem.News, NavItem.Explore, NavItem.Bookmarks))
        val screenState = ScreenState(Screen.News)

        val navigator = object : Navigator {
            override fun navigateTo(screen: Screen) {
                screenState.currentScreen = screen
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) }
                )
            },
            bodyContent = {
                when (screenState.currentScreen) {
                    is Screen.News -> {
                        NewsContent(
                            newsContainer = appContainer.newsContainer(),
                            onItemClicked = {
                                // Handle click listener
                            }
                        )
                        appContainer.newsContainer().newsViewModel.value.fetchHeadlines()
                    }
                    else -> Unit
                }

            },
            bottomBar = {
                BottomNavBar(
                    state = navBarState,
                    onItemSelected = {
                        setBottomNavSelection(it, navigator)
                    }
                )
            }
        )
    }

    private fun setBottomNavSelection(navItem: NavItem, navigator: Navigator): Boolean {
        // navigate to corresponding screen
        when (navItem) {
            is NavItem.News -> navigator.navigateTo(Screen.News)
            else -> Toast.makeText(this, "Not yet implemented ${navItem.index}", Toast.LENGTH_SHORT)
                .show()
        }
        return true
    }
}
