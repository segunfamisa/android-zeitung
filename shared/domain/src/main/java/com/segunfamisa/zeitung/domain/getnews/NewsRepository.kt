package com.segunfamisa.zeitung.domain.getnews

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Interface for fetching news
 */
interface NewsRepository {

    fun getNews(sourceIds: List<String>, page: Int, from: Date?): Flow<Either<Error, List<Article>>>
}
