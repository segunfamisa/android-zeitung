package com.segunfamisa.zeitung.domain.getnews

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import java.util.Date

/**
 * Interface for fetching news
 */
interface NewsRepository {

    suspend fun getNews(sourceIds: List<String>, page: Int, from: Date?): Either<Error, List<Article>>
}
