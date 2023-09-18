package com.segunfamisa.zeitung.common

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
): AppState {
    return remember(windowSizeClass) {
        AppState(
            windowHeightSizeClass = windowSizeClass.heightSizeClass,
            windowWidthSizeClass = windowSizeClass.widthSizeClass
        )
    }
}

val LocalAppState = compositionLocalOf {
    AppState(
        windowWidthSizeClass = WindowWidthSizeClass.Compact,
        windowHeightSizeClass = WindowHeightSizeClass.Compact
    )
}

@Stable
data class AppState(
    val windowWidthSizeClass: WindowWidthSizeClass,
    val windowHeightSizeClass: WindowHeightSizeClass,
) {

    val windowStyle: WindowStyle
        get() {
            return if (windowWidthSizeClass == WindowWidthSizeClass.Compact) {
                WindowStyle.OnePane
            } else {
                WindowStyle.TwoPane
            }
        }

    val shouldUseNavRail: Boolean
        get() = windowStyle == WindowStyle.TwoPane

    val shouldUseBottomBar: Boolean
        get() = !shouldUseNavRail
}

@JvmInline
value class WindowStyle private constructor(
    private val value: Int,
) {
    companion object {
        val OnePane = WindowStyle(1)
        val TwoPane = WindowStyle(2)
    }

    override fun toString(): String {
        return when (this) {
            OnePane -> "OnePane"
            TwoPane -> "TwoPane"
            else -> ""
        }
    }
}
