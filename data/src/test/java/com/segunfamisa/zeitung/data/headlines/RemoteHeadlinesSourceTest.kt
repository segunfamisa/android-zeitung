package com.segunfamisa.zeitung.data.headlines

import arrow.core.orNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.data.remote.ApiService
import com.segunfamisa.zeitung.data.sources.remote.TestDataGenerator
import com.segunfamisa.zeitung.data.remote.entities.ArticlesResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class RemoteHeadlinesSourceTest {

    private val apiService: ApiService = mock()
    private val mapper = ArticlesMapper()

    private val source = RemoteHeadlinesSource(apiService = apiService, mapper = mapper)

    @Test
    fun `headlines are fetched from api service when we pass at least one valid parameter`() = runBlocking {
        // given that we search for a certain parameter, category
        val category = "technology"

        // given that the api service can return results for this category
        val articles = TestDataGenerator.createArticles(count = 5)
        whenever(apiService.getHeadlines(category = category))
            .thenReturn(async {
                Response.success(
                    ArticlesResponse(
                        totalResults = 5,
                        articles = articles
                    )
                )
            })

        // when we get headlines with this category
        val result = source.getHeadlines(category = category, country = "", sources = "")

        val headlines = result.orNull()!!

        // the verify that the headlines are as expected
        assertEquals(5, headlines.size)
    }

    @Test
    fun `headlines are not fetched if all parameters are empty`() = runBlocking {
        // given that all params are empty and we get headlines,
        val result = source.getHeadlines(category = "", country = "", sources = "")

        // then we receive an error
        assertTrue(result.isLeft())
    }

    @Test
    fun `headlines are not fetched if we search by both sources and category`() = runBlocking {
        // given that both sources and category are non empty
        // when we get headlines
        val result = source.getHeadlines(category = "business", country = "", sources = "local.de,news.google.de")

        // then we receive error
        assertTrue(result.isLeft())
    }

    @Test
    fun `headlines are not fetched if we search by both sources and country`() = runBlocking {
        // given that both sources and country are non empty
        // when we get headlines
        val result = source.getHeadlines(category = "", country = "de", sources = "local.de,news.google.de")

        // then we receive error
        assertTrue(result.isLeft())
    }
}