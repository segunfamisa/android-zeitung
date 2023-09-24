package com.segunfamisa.zeitung.ui.common.nav

object Routes {
    private const val host = "zeitung"
    val Onboarding = format("onboarding")
    val Main = format("main")
    val News = format("news")
    val Sources = format("sources")
    val Bookmarks = format("bookmarks")

    private fun format(screen: String): String {
        return "$host://$screen"
    }
}