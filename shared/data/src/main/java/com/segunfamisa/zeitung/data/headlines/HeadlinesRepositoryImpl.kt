package com.segunfamisa.zeitung.data.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.data.di.qualifiers.Remote
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.headlines.HeadlinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class HeadlinesRepositoryImpl @Inject constructor(
    @Remote private val remoteSource: HeadlinesSource
) : HeadlinesRepository {

    override fun getHeadlines(
        category: String,
        country: String,
        sources: String,
        page: Int
    ): Flow<Either<Error, List<Article>>> {
        return flow {
            this.emit(
                remoteSource.getHeadlines(
                    category = category,
                    country = country,
                    sources = sources,
                    page = page,
                ).map {
                    it.articles
                }
            )
        }
    }
}
