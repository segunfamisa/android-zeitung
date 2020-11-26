package com.segunfamisa.zeitung.domain.getnews

import arrow.core.Either
import arrow.core.orNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.domain.utils.ArticleCreator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetNewsUseCaseTest {

    private val repo = mock<NewsRepository>()
    private lateinit var useCase: GetNewsUseCase

    @Before
    fun setUp() {
        useCase = GetNewsUseCase(repository = repo)
    }

    @Test
    fun `the repository is called to fetch news with the correct parameters`() = runBlocking {
        // given the news sources
        val sources = listOf("bbc-news", "cnn-brk")

        // given the repository
        val articles = ArticleCreator.createArticles(count = 2)
        whenever(repo.getNews(sourceIds = sources, page = 0, from = null))
            .thenReturn(flowOf(Either.right(articles)))

        // when we invoke, then we we receive the use case
        useCase.execute(param = NewsQueryParam(sourceIds = sources, page = 0, from = null))
            .collect { result ->
                // then we assert that the retrieved articles are the same content as emitted by the repo
                val retrievedArticles = result.orNull()!!
                assertEquals(
                    "the content of the retrieved articles vary from the articles returned by the repository",
                    articles,
                    retrievedArticles
                )
            }
    }
}