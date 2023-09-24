package com.segunfamisa.zeitung.robots.bookmarks

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import com.segunfamisa.zeitung.helpers.ComposeRobot

class BookmarksScreenRobot(
    private val composeTestRule: AndroidComposeTestRule<*, *>
) : ComposeRobot(composeTestRule) {

    fun BookmarksScreenRobot.fling() : BookmarksScreenRobot {
        composeTestRule.waitForIdle()
        return this
    }

    companion object {

        @JvmStatic
        fun AndroidComposeTestRule<*, *>.onBookmarks(actions: BookmarksScreenRobot.() -> BookmarksScreenRobot): BookmarksScreenRobot {
            return BookmarksScreenRobot(composeTestRule = this)
                .apply {
                    actions()
                }
        }
    }
}
