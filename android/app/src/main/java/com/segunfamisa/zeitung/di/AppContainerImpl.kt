package com.segunfamisa.zeitung.di

import javax.inject.Inject

class AppContainerImpl @Inject constructor(
    private val newsContainer: NewsContainer
) : AppContainer {

    override fun newsContainer(): NewsContainer = newsContainer
}