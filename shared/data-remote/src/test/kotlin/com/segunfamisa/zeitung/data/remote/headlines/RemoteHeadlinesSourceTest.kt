package com.segunfamisa.zeitung.data.remote.headlines

import arrow.core.orNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.data.remote.common.ApiCallError
import com.segunfamisa.zeitung.data.remote.common.ApiResponse
import com.segunfamisa.zeitung.data.remote.entities.ArticlesResponse
import com.segunfamisa.zeitung.data.remote.service.ApiService
import com.segunfamisa.zeitung.data.remote.service.TestDataGenerator
import com.segunfamisa.zeitung.data.remote.utils.assertThrows
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class RemoteHeadlinesSourceTest {

    private val apiService: ApiService = mock()
    private val mapper = ArticlesMapper()

    private val source = RemoteHeadlinesSource(apiService = apiService, mapper = mapper)

    @Test
    fun `headlines are fetched from api service when we pass at least one valid parameter`() =
        runBlocking {
            // given that we search for a certain parameter, category
            val category = "technology"

            // given that the api service can return results for this category
            val articles = TestDataGenerator.createArticles(count = 5)
            whenever(apiService.getHeadlines(category = category))
                .thenReturn(
                    ApiResponse.Success(
                        entity = ArticlesResponse(
                            totalResults = 5,
                            articles = articles
                        )
                    )
                )

            // when we get headlines with this category
            val result = source.getHeadlines(category = category, country = "", sources = "")

            val headlines = result.orNull()!!

            // the verify that the headlines are as expected
            assertEquals(5, headlines.size)
        }

    @Test
    fun `illegal argument exception is thrown if all parameters are empty`() = runBlocking {
        // given that all params are empty and we get headlines,
        val actualError = assertThrows<IllegalArgumentException> {
            source.getHeadlines(category = "", country = "", sources = "")
        }

        // then we receive an error
        assertEquals(
            "Invalid request, no parameter is specified",
            actualError.message
        )
    }

    @Test
    fun `illegal argument exception is thrown if we search by both sources and category`() =
        runBlocking {
            // given that both sources and category are non empty
            // when we get headlines
            val actualError = assertThrows<IllegalArgumentException> {
                source.getHeadlines(
                    category = "business",
                    country = "",
                    sources = "local.de,news.google.de"
                )
            }

            // then we assert the error message is as expected
            assertEquals(
                "Invalid request, can't search category and sources together",
                actualError.message
            )
        }

    @Test
    fun `illegal argument exception is thrown when page is less than 0`() = runBlocking {
        val actualError = assertThrows<IllegalArgumentException> {
            source.getHeadlines(
                category = "",
                country = "",
                sources = "local.de,news.google.de",
                page = -1
            )
        }
        val expectedMessage = "Page cannot be null or less than 0. Current value: -1"
        assertEquals(expectedMessage, actualError.message)
    }

    @Test
    fun `illegal argument exception is thrown if we search by both sources and country`() =
        runBlocking {
            // given that both sources and country are non empty
            // when we get headlines
            val actualError = assertThrows<IllegalArgumentException> {
                source.getHeadlines(
                    category = "",
                    country = "de",
                    sources = "local.de,news.google.de"
                )
            }

            // then the error is as expected
            assertEquals(
                "Invalid request, can't search country and sources together",
                actualError.message
            )
        }

    @Test
    fun `an error is returned the api returns an error`() = runBlocking {
        // given that we search for a certain valid param
        val category = "technology"

        // given that the api call fails
        whenever(apiService.getHeadlines(category = category)).thenReturn(
            ApiResponse.Error(
                throwable = ApiCallError.ServerError(errorCode = 401, message = "API call failure")
            )
        );

        // when we get headlines for this category
        val result = source.getHeadlines(category = category, country = "", sources = "")

        // then assert that it's an error
        assertTrue(result.isLeft())
    }
}