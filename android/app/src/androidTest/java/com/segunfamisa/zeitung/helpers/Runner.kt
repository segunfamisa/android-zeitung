package com.segunfamisa.zeitung.helpers

import androidx.compose.ui.test.junit4.AndroidComposeTestRule

fun AndroidComposeTestRule<*, *>.runTest(
    testBody: AndroidComposeTestRule<*, *>.() -> Unit
) {
    testBody(this)
}
