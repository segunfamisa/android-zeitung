package com.segunfamisa.zeitung.data.remote.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.data.common.IllegalOperationException
import com.segunfamisa.zeitung.data.headlines.HeadlinesSource
import com.segunfamisa.zeitung.data.remote.common.ApiResponse
import com.segunfamisa.zeitung.data.remote.service.ApiService
import com.segunfamisa.zeitung.domain.common.Error
import javax.inject.Inject

internal class RemoteHeadlinesSource @Inject constructor(
    private val apiService: ApiService,
    private val mapper: ArticlesMapper
) : HeadlinesSource {

    override suspend fun getHeadlines(
        category: String,
        country: String,
        sources: String,
        pageSize: Int?,
        page: Int?,
    ): Either<Error, List<Article>> {
        return executeIfValid(
            category = category,
            country = country,
            sources = sources,
            page = page,
        ) { cat, cry, src ->
            try {
                val response = apiService.getHeadlines(
                    category = cat.nullify(),
                    country = cry.nullify(),
                    sources = src.nullify(),
                    pageSize = pageSize,
                    page = page
                )

                when (response) {
                    is ApiResponse.Success -> Either.Right(
                        response.entity
                            .articles.map {
                                mapper.from(data = it)
                            })

                    is ApiResponse.Error -> Either.Left(
                        Error(
                            message = response.throwable.message ?: "",
                            throwable = response.throwable
                        )
                    )
                }
            } catch (e: Exception) {
                Either.Left(Error(message = e.message ?: ""))
            }
        }
    }

    private suspend fun executeIfValid(
        category: String,
        country: String,
        sources: String,
        page: Int?,
        action: suspend (String, String, String) -> Either<Error, List<Article>>
    ): Either<Error, List<Article>> {
        if (category.isEmpty() && country.isEmpty() && sources.isEmpty()) {
            val message = "Invalid request, no parameter is specified"
            return Either.Left(
                Error(
                    message = message,
                    throwable = IllegalOperationException(message)
                )
            )
        }

        if (sources.isNotEmpty()) {
            val message: String
            if (category.isNotEmpty()) {
                message = "Invalid request, can't search category and sources together"
                return Either.Left(
                    Error(
                        message = message,
                        throwable = IllegalOperationException(message)
                    )
                )
            }

            if (country.isNotEmpty()) {
                message = "Invalid request, can't search country and sources together"
                return Either.Left(
                    Error(
                        message = message,
                        throwable = IllegalOperationException(message)
                    )
                )
            }
        }

        if (page != null && page < 0)
            throw IllegalArgumentException("Page cannot be null or less than 0. Current value: $page")

        return action(category, country, sources)
    }

    private fun String.nullify(): String? {
        return if (this.isEmpty()) {
            null
        } else {
            this
        }
    }
}
