package com.segunfamisa.zeitung.data.remote.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.data.newssources.NewsSourcesDataSource
import com.segunfamisa.zeitung.data.remote.common.ApiResponse
import com.segunfamisa.zeitung.data.remote.service.ApiService
import com.segunfamisa.zeitung.domain.common.Error
import javax.inject.Inject

internal class RemoteNewsSourcesDataSource @Inject constructor(
    private val apiService: ApiService,
    private val mapper: SourcesMapper
) : NewsSourcesDataSource {

    override suspend fun getNewsSources(
        category: String,
        language: String,
        country: String
    ): Either<Error, List<Source>> {
        val response = apiService.getSources(
            category = category,
            language = language,
            country = country
        )

        return when (response) {
            is ApiResponse.Success -> {
                val sources = response.entity.sources.map { apiSource ->
                    mapper.from(data = apiSource)
                }
                Either.Right(sources)
            }

            is ApiResponse.Error -> Either.left(
                Error(
                    message = response.throwable.message ?: "",
                    throwable = response.throwable
                )
            )
        }
    }
}
