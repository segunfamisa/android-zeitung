package com.segunfamisa.zeitung.data.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Okio
import java.nio.charset.StandardCharsets

class MockApi {

    val successDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path
            return when {
                path.contains("/everything") -> createResponseFromFile(file = "everything.json")
                path.contains("/sources") -> createResponseFromFile(file = "news-sources.json")
                path.contains("/top-headlines") -> createResponseFromFile(file = "top-headlines.json")
                else -> throw IllegalArgumentException("We have not handled this path")
            }
        }
    }

    val errorDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return createResponseFromFile(file = "error.json")
        }
    }

    fun createServer(): MockWebServer = MockWebServer()

    private fun createResponseFromFile(file: String): MockResponse {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("responses/$file")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(StandardCharsets.UTF_8))
        return mockResponse
    }
}
