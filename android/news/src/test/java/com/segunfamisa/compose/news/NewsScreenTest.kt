package com.segunfamisa.compose.news

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.annotation.ExperimentalCoilApi
import com.github.takahirom.roborazzi.captureRoboImage
import com.segunfamisa.zeitung.news.NewsContent
import com.segunfamisa.zeitung.news.TEST_TAG_ARTICLE_LIST
import com.segunfamisa.zeitung.news.TEST_TAG_LOADING
import com.segunfamisa.zeitung.news.ui.NewsUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoilApi::class)
class NewsScreenTest {

    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `when ui state is loading, show loading indicator`() {
        composeTestRule.setContent {
            NewsContent(uiState = NewsUiState.Loading)
        }

        composeTestRule.onNodeWithTag(TEST_TAG_LOADING)
            .assertExists("Loading indicator not found")
    }

    @Test
    fun `when ui state is loading, show news items list`() {
        composeTestRule.setContent {
            NewsContent(uiState = UiStateFactory.createLoadedState(newsCount = 3))
        }

        composeTestRule.onRoot().captureRoboImage()

        composeTestRule.onNodeWithTag(TEST_TAG_ARTICLE_LIST)
            .assertExists("News list not found")
    }
}