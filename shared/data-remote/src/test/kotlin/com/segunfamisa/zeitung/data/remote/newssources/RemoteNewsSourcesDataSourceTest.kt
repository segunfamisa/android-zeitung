package com.segunfamisa.zeitung.data.remote.newssources

import arrow.core.orNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.data.remote.common.ApiResponse
import com.segunfamisa.zeitung.data.remote.entities.SourcesResponse
import com.segunfamisa.zeitung.data.remote.service.ApiService
import com.segunfamisa.zeitung.data.remote.service.TestDataGenerator
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RemoteNewsSourcesDataSourceTest {

    private val apiService: ApiService = mock()
    private val mapper = SourcesMapper()

    private val remoteDataService =
        RemoteNewsSourcesDataSource(apiService = apiService, mapper = mapper)

    @Test
    fun `get news sources from the api service`() = runBlocking {
        // given the query params
        val category = "business"
        val language = "en"
        val country = ""

        // given that the api service returns news sources for the query params
        val apiNewsSources = TestDataGenerator.createSources(5)
        whenever(apiService.getSources(category = category, country = country, language = language))
            .thenReturn(ApiResponse.Success(SourcesResponse(sources = apiNewsSources)))

        // when we get news sources
        val response = remoteDataService.getNewsSources(
            category = category,
            country = country,
            language = language
        )

        // then we verify that the response is successful and data matches what we expect
        val sources = response.orNull()!!
        assertEquals(5, sources.size)
    }
}
