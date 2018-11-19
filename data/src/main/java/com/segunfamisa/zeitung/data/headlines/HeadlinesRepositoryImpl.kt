package com.segunfamisa.zeitung.data.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result
import com.segunfamisa.zeitung.domain.headlines.HeadlinesRepository
import javax.inject.Inject

internal class HeadlinesRepositoryImpl @Inject constructor(
    private val remoteSource: HeadlinesSource
) : HeadlinesRepository {
    override suspend fun getHeadlines(
        category: String,
        country: String,
        sources: String
    ): Either<Error, Result<List<Article>>> {
        return remoteSource.getHeadlines(category = category, country = "", sources = "")
            .map {
                Result(data = it)
            }
    }
}
