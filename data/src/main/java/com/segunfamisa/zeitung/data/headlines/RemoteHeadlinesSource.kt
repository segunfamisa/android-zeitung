package com.segunfamisa.zeitung.data.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.data.common.IllegalOperationException
import com.segunfamisa.zeitung.data.remote.ApiService
import com.segunfamisa.zeitung.domain.common.Error
import javax.inject.Inject

internal class RemoteHeadlinesSource @Inject constructor(
    private val apiService: ApiService,
    private val mapper: ArticlesMapper
) : HeadlinesSource {

    override suspend fun getHeadlines(
        category: String,
        country: String,
        sources: String
    ): Either<Error, List<Article>> {
        return executeIfValid(
            category = category,
            country = country,
            sources = sources
        ) { cat, cry, src ->
            try {
                val response = apiService.getHeadlines(
                    category = cat.nullify(),
                    country = cry.nullify(),
                    sources = src.nullify()
                )

                val headlines = response.body()
                if (headlines != null) {
                    Either.Right(headlines.articles.map { mapper.from(data = it) })
                } else {
                    // TODO parse the actual response
                    Either.Left(Error(message = "Something wen't wrong"))
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
        action: suspend (String, String, String) -> Either<Error, List<Article>>
    ): Either<Error, List<Article>> {
        if (category.isEmpty() && country.isEmpty() && sources.isEmpty()) {
            val message = "Invalid request, no parameter is specified"
            return Either.Left(Error(message = message, throwable = IllegalOperationException(message)))
        }

        if (sources.isNotEmpty()) {
            val message: String
            if (category.isNotEmpty()) {
                message = "Invalid request, can't search category and sources together"
                return Either.Left(Error(message = message, throwable = IllegalOperationException(message)))
            }

            if (country.isNotEmpty()) {
                message = "Invalid request, can't search country and sources together"
                return Either.Left(Error(message = message, throwable = IllegalOperationException(message)))
            }
        }

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
