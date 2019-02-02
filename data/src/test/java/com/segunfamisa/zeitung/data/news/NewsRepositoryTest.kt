package com.segunfamisa.zeitung.data.news

import arrow.core.Either
import arrow.core.getOrHandle
import arrow.core.orNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.news.NewsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date
import kotlin.random.Random

class NewsRepositoryTest {

    private val remoteSource: NewsSource = mock()
    private lateinit var repository: NewsRepository

    @Before
    fun setUp() {
        repository = NewsRepositoryImpl(remoteSource = remoteSource)
    }

    @Test
    fun `news are fetched over network from correct news sources`() = runBlocking {
        // given that we pass a list of news sources
        val sources = listOf("cnn-brk", "bbc-world", "fox-news")

        // given that the api source can return articles based on correctly formatted news sources
        val articles = createArticles(7)
        whenever(remoteSource.getNews(sources = "cnn-brk,bbc-world,fox-news", page = 0, from = null))
            .thenReturn(Either.right(articles))

        // when we call the repository to get news sources
        val result = repository.getNews(sourceIds = sources, page = 0, from = null)

        // then we verify that we return the data that the remote source fetches
        assertEquals(
            "emitted articles are not the ones from the remote source",
            articles,
            result.orNull()
        )
    }

    @Test
    fun `error is returned if list of sources is empty`() = runBlocking {
        // when we call the repository to get news sources with an empty list of source ids
        val result = repository.getNews(sourceIds = listOf(), page = 0, from = null)

        // then we verify that we return the data that the remote source fetches
        assertTrue(
            "error should be returned when list of sources is empty",
            result.getOrHandle { it } is Error
        )
    }

    @Test
    fun `news are fetched over network for the correct date`() = runBlocking {
        // given that we pass a valid date
        val startDate = Date()

        // given that the api source can return articles based on correct date
        val articles = createArticles(7)
        whenever(remoteSource.getNews(sources = "cnn-brk", page = 0, from = startDate))
            .thenReturn(Either.right(articles))

        // when we call the repository to get news sources
        val result = repository.getNews(sourceIds = listOf("cnn-brk"), page = 0, from = startDate)

        // then we verify that we return the data that the remote source fetches
        assertEquals(
            "emitted articles are not for the correct date",
            articles,
            result.orNull()
        )
    }

    @Test
    fun `news are fetched over network for the correct page`() = runBlocking {
        // given that we pass a valid page
        val page = Random.nextInt()

        // given that the api source can return articles based on correct page
        val articles = createArticles(7)
        whenever(remoteSource.getNews(sources = "bbc-africa", page = page, from = null))
            .thenReturn(Either.right(articles))

        // when we call the repository to get news sources
        val result = repository.getNews(sourceIds = listOf("bbc-africa"), page = page, from = null)

        // then we verify that we return the data that the remote source fetches
        assertEquals(
            "emitted articles are not for the correct page",
            articles,
            result.orNull()
        )
    }

    @Test
    fun `an error is returned when the remote source has an error`() = runBlocking {
        // given that we pass a list of news sources
        val sources = listOf("cnn-brk", "bbc-world", "fox-news")

        // given that the remote source gives an error
        whenever(remoteSource.getNews(sources = "cnn-brk,bbc-world,fox-news", page = 0, from = null))
            .thenReturn(Either.left(Error(message = "can't retrieve news", throwable = Throwable())))

        // when we call the repository to get news sources
        val result = repository.getNews(sourceIds = sources, page = 0, from = null)

        // then verify that there is an error
        assertTrue(result.getOrHandle { it } is Error)
    }

    private fun createArticles(count: Int): List<Article> {
        val articles = mutableListOf<Article>()
        repeat(count) {
            articles.add(
                Article(
                    source = Source(
                        id = "source $it",
                        country = "country $it",
                        language = "language $it",
                        category = "category $it",
                        url = "url $it",
                        description = "description $it",
                        name = "name $it"
                    ),
                    author = "author $it",
                    title = "title $it",
                    description = "description $it",
                    url = "url $it",
                    imageUrl = "imageUrl $it",
                    publishedAt = Date(System.currentTimeMillis() + count),
                    content = "content $it"
                )
            )
        }

        return articles
    }
}