package com.segunfamisa.zeitung.data.news

import arrow.core.getOrHandle
import arrow.core.orNull
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.data.common.ErrorParser
import com.segunfamisa.zeitung.data.remote.ApiKeyProvider
import com.segunfamisa.zeitung.data.remote.ApiService
import com.segunfamisa.zeitung.data.remote.ApiServiceCreator
import com.segunfamisa.zeitung.data.remote.AuthorizationInterceptor
import com.segunfamisa.zeitung.data.remote.UrlProvider
import com.segunfamisa.zeitung.data.sources.remote.MockApi
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.text.SimpleDateFormat

class RemoteNewsSourceTest {

    companion object {

        private val mockApi = MockApi()
        private lateinit var server: MockWebServer

        @JvmStatic
        @BeforeClass
        fun setUpServer() {
            server = mockApi.createServer()
            server.start()
        }

        @JvmStatic
        @AfterClass
        fun tearDownServer() {
            server.shutdown()
        }
    }

    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
    private lateinit var apiService: ApiService
    private val errorParser: ErrorParser = mock()

    private lateinit var source: RemoteNewsSource

    @Before
    fun setUp() {
        apiService = ApiServiceCreator(
            urlProvider = object : UrlProvider {
                override fun getBaseUrl(): String {
                    return server.url("/").toString()
                }
            },
            authorizationInterceptor = AuthorizationInterceptor(apiKeyProvider = object : ApiKeyProvider {
                override fun getApiKey(): String {
                    return "secret_key"
                }
            })
        ).createService()

        whenever(errorParser.parse(any())).thenReturn(
            com.segunfamisa.zeitung.domain.common.Error(
                "An error occurred",
                throwable = Throwable("Error")
            )
        )

        source = RemoteNewsSource(apiService = apiService, errorParser = errorParser)
    }

    @Test
    fun `news are fetched from api service when we pass news sources`() = runBlocking {
        // given we search for news from bbc and cnn
        val newsSources = "bbc-news,cnn-brk"

        // given that the api can return news for whatever parameter
        server.setDispatcher(mockApi.successDispatcher)

        // when we search for the sources
        val result = source.getNews(sources = newsSources, page = 0, from = null)

        // then verify that the articles are as expected
        val articles = result.orNull()!!

        // verify that the articles contain 20 results from (resources/responses/everything.json)
        assertEquals(20, articles.size)

        // verify that the 3rd article corresponds to the third article mapped from the json file
        val expected3rdArticle = Article(
            source = Source(
                id = "engadget",
                name = "Engadget",
                description = "",
                url = "",
                category = "",
                language = "",
                country = ""
            ),
            author = "Kris Holt",
            title = "Vertu rises from bankruptcy ashes with \$4,000 Android phone",
            description = "Like a v,s,df",
            url = "https://www.engadget.com/2018/10/18/vertu-aster-p-android-bankruptcy-return/",
            imageUrl = "https://o.aolcdn.com/images/dims5",
            publishedAt = sdf.parse("2018-10-18T19:52:00Z"),
            content = "The 4.9-inch display [+1379 chars]"
        )
        assertEquals(
            "the 3rd article returned does not correspond to that in the json response",
            expected3rdArticle,
            articles[2]
        )
    }

    @Test
    fun `an error is returned when api runs into an error`() = runBlocking {
        // given we search for news from bbc and cnn
        val newsSources = "bbc-news,cnn-brk"

        // given that the api fails
        server.setDispatcher(mockApi.errorDispatcher)

        // when we search for the sources
        val result = source.getNews(sources = newsSources, page = 0, from = null)

        // then verify that the result is an error
        assertTrue(result.getOrHandle { it } is Error)
    }
}
