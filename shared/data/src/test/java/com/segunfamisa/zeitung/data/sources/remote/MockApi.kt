package com.segunfamisa.zeitung.data.sources.remote

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
                path.contains("/everything") -> createSuccessResponseFromFile(file = "everything.json")
                path.contains("/sources") -> createSuccessResponseFromFile(file = "news-sources.json")
                path.contains("/top-headlines") -> createSuccessResponseFromFile(file = "top-headlines.json")
                else -> throw IllegalArgumentException("We have not handled this path")
            }
        }
    }

    val errorDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return createErrorResponseFromFile(file = "error.json")
        }
    }

    fun createServer(): MockWebServer = MockWebServer()

    private fun createSuccessResponseFromFile(file: String): MockResponse {
        return createResponseFromFile(file = file, response = MockResponse())
    }

    private fun createErrorResponseFromFile(file: String): MockResponse {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(400)
        return createResponseFromFile(file = file, response = mockResponse)
    }

    private fun createResponseFromFile(file: String, response: MockResponse): MockResponse {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("responses/$file")
        val source = Okio.buffer(Okio.source(inputStream))
        response.setBody(source.readString(StandardCharsets.UTF_8))
        return response
    }
}
