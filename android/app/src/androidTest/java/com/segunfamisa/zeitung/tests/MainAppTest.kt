package com.segunfamisa.zeitung.tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.segunfamisa.zeitung.helpers.runTest
import com.segunfamisa.zeitung.robots.main.MainScreenRobot.Companion.onMain
import com.segunfamisa.zeitung.ui.common.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testRobotPatternApi() = composeTestRule.runTest {
        onMain {
            clickSourcesTab(waitForData = true)
            clickBookmarksTab(waitForData = true)
            clickHomeTab()
            clickSourcesTab()
            assertLoading()
        }
    }
}
