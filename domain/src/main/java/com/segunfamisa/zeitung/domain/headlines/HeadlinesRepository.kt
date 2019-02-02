package com.segunfamisa.zeitung.domain.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error

interface HeadlinesRepository {

    suspend fun getHeadlines(category: String, country: String, sources: String): Either<Error, List<Article>>
}
