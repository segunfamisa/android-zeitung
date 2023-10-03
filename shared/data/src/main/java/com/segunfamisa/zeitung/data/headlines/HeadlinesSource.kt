package com.segunfamisa.zeitung.data.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.ArticlesResult
import com.segunfamisa.zeitung.domain.common.Error

interface HeadlinesSource {

    suspend fun getHeadlines(
        category: String,
        country: String,
        sources: String,
        pageSize: Int? = null,
        page: Int? = null,
    ): Either<Error, ArticlesResult>
}