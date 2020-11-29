package com.segunfamisa.zeitung.ui.common

object Routes {
    private const val host = "zeitung"
    val Onboarding = format("onboarding")
    val Main = format("main")
    val News = format("news")
    val Explore = format("explore")
    val Bookmarks = format("bookmarks")

    private fun format(screen: String): String {
        return "$host://$screen"
    }
}