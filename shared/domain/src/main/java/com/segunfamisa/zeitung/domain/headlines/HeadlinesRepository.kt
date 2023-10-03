package com.segunfamisa.zeitung.domain.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow

interface HeadlinesRepository {

    fun getHeadlines(
        category: String,
        country: String,
        sources: String,
        page: Int,
    ): Flow<Either<Error, List<Article>>>
}
