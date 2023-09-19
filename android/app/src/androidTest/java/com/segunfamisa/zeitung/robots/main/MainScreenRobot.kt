package com.segunfamisa.zeitung.robots.main

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.helpers.ComposeRobot


internal class MainScreenRobot private constructor(
    private val composeTestRule: AndroidComposeTestRule<*, *>
) : ComposeRobot(composeTestRule) {

    fun MainScreenRobot.clickSourcesTab(waitForData: Boolean = false): MainScreenRobot {
        return performClickOnNavTab(
            text = composeTestRule.activity.getString(R.string.menu_sources),
            waitForData = waitForData
        )
    }

    fun MainScreenRobot.clickBookmarksTab(waitForData: Boolean = false): MainScreenRobot {
        performClickOnNavTab(
            text = composeTestRule.activity.getString(R.string.menu_bookmarks),
            waitForData
        )
        return this
    }

    fun MainScreenRobot.clickHomeTab(waitForData: Boolean = false): MainScreenRobot {
        performClickOnNavTab(
            text = composeTestRule.activity.getString(R.string.menu_news),
            waitForData = waitForData
        )
        return this
    }

    fun MainScreenRobot.assertLoading(): MainScreenRobot {
        // TODO yet to be implemented
        return this
    }

    private fun performClickOnNavTab(text: String, waitForData: Boolean): MainScreenRobot {
        findTab(text = text)
            .performClick()
        if (waitForData) {
            composeTestRule.waitForIdle()
        }
        return this
    }

    companion object {

        @JvmStatic
        fun AndroidComposeTestRule<*, *>.onMain(actions: MainScreenRobot.() -> MainScreenRobot): MainScreenRobot {
            return MainScreenRobot(composeTestRule = this)
                .apply {
                    actions()
                }
        }
    }
}
