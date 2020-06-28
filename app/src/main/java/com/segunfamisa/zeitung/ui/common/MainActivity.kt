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
import androidx.ui.res.vectorResource
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) }
                )
            },
            bodyContent = {
                MainContent(
                    items = navItems,
                    state = NavBarState(navItems[0]),
                    onItemSelected = {
                        setBottomNavSelection(it)
                    }
                )
            }
        )
    }

    @Composable
    private val navItems: List<NavItem>
        get() = listOf(
            NavItem(
                0,
                stringResource(id = R.string.menu_news),
                vectorResource(id = R.drawable.ic_nav_menu_newspaper)
            ),

            NavItem(
                1,
                stringResource(id = R.string.menu_bookmarks),
                vectorResource(id = R.drawable.ic_nav_menu_bookmark)
            )
        )

    private fun setBottomNavSelection(navItem: NavItem): Boolean {
        Toast.makeText(this, "Not yet implemented ${navItem.index}", Toast.LENGTH_SHORT).show()
        return true
    }
}
