package com.segunfamisa.zeitung.data.remote.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.ArticlesResult
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
    ): Either<Error, ArticlesResult> {
        return executeIfValid(
            category = category,
            country = country,
            sources = sources,
            page = page,
        ) { ->
            try {
                val response = apiService.getHeadlines(
                    category = category.nullify(),
                    country = country.nullify(),
                    sources = sources.nullify(),
                    pageSize = pageSize,
                    page = page
                )

                when (response) {
                    is ApiResponse.Success -> Either.Right(
                        ArticlesResult(
                            totalResults = response.entity.totalResults,
                            articles = response.entity
                                .articles.map {
                                    mapper.from(data = it)
                                })
                    )

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
        action: suspend () -> Either<Error, ArticlesResult>
    ): Either<Error, ArticlesResult> {
        if (category.isEmpty() && country.isEmpty() && sources.isEmpty()) {
            throw IllegalArgumentException("Invalid request, no parameter is specified")
        }

        if (sources.isNotEmpty()) {
            if (category.isNotEmpty()) {
                throw IllegalArgumentException("Invalid request, can't search category and sources together")
            }

            if (country.isNotEmpty()) {
                throw IllegalArgumentException("Invalid request, can't search country and sources together")
            }
        }

        if (page != null && page < 0)
            throw IllegalArgumentException("Page cannot be null or less than 0. Current value: $page")

        return action()
    }

    private fun String.nullify(): String? {
        return if (this.isEmpty()) {
            null
        } else {
            this
        }
    }
}
