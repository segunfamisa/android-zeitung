package com.segunfamisa.zeitung.ui.common

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.ui.theme.ZeitungTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZeitungTheme {
                MainScreen()
            }
        }
    }

    @Composable
    @Preview
    private fun MainScreen() {
        val navBarState = NavBarState(listOf(NavItem.News, NavItem.Explore, NavItem.Bookmarks))
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) }
                )
            },
            bodyContent = {
                MainContent(
                    onNavItemSelected = {
                        setBottomNavSelection(it)
                    },
                    navBarState = navBarState
                )
            }
        )
    }

    private fun setBottomNavSelection(navItem: NavItem): Boolean {
        Toast.makeText(this, "Not yet implemented ${navItem.index}", Toast.LENGTH_SHORT).show()
        return true
    }
}
