package com.segunfamisa.zeitung.helpers

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher.Companion.expectValue
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.text.AnnotatedString

open class ComposeRobot(
    private val composeRule: ComposeTestRule
) {

    protected fun ComposeRobot.findTab(text: String): SemanticsNodeInteraction {
        val tabs = composeRule.onNode(expectValue(SemanticsProperties.SelectableGroup, Unit))
            .onChildren()
            .filter(expectValue(SemanticsProperties.Role, Role.Tab))

        return tabs.filterToOne(
            expectValue(
                SemanticsProperties.Text,
                listOf(AnnotatedString(text))
            )
        )
    }
}
