package com.segunfamisa.zeitung.data.news

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.data.common.IllegalOperationException
import com.segunfamisa.zeitung.data.di.qualifiers.Remote
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.getnews.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

internal class NewsRepositoryImpl @Inject constructor(
    @Remote private val remoteSource: NewsSource
) : NewsRepository {

    override fun getNews(
        sourceIds: List<String>,
        page: Int,
        from: Date?
    ): Flow<Either<Error, List<Article>>> {
        return flow {
            val result = sourceIds.takeIf { it.isNotEmpty() }
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

            this.emit(result)
        }
    }
}
