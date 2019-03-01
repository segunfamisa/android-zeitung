package com.segunfamisa.zeitung.data.news

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.data.common.IllegalOperationException
import com.segunfamisa.zeitung.data.di.qualifiers.DataSource
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.news.NewsRepository
import java.util.Date
import javax.inject.Inject

internal class NewsRepositoryImpl @Inject constructor(
    @DataSource(type = "remote") private val remoteSource: NewsSource
) : NewsRepository {

    override suspend fun getNews(sourceIds: List<String>, page: Int, from: Date?): Either<Error, List<Article>> {
        return sourceIds.takeIf { it.isNotEmpty() }
            ?.asSequence()
            ?.filterNot { it.isEmpty() }
            ?.map { it.trim() }
            ?.joinToString(",")
            ?.let { sources ->
                remoteSource.getNews(sources = sources, page = page, from = from)
            } ?: Either.left(
            Error(
                message = "List of sources cannot be empty",
                throwable = IllegalOperationException("List of sources cannot be empty")
            )
        )
    }
}
