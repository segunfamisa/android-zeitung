package com.segunfamisa.zeitung.data.news

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import java.util.Date

internal interface NewsSource {

    suspend fun getNews(
        sources: String,
        page: Int,
        from: Date?
    ): Either<Error, List<Article>>
}
