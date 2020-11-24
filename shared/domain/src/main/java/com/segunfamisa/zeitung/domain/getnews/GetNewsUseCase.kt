package com.segunfamisa.zeitung.domain.getnews

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.UseCase
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) : UseCase<NewsQueryParam, List<Article>>() {
    override suspend fun invoke(param: NewsQueryParam): Either<Error, List<Article>> {
        return repository.getNews(
            sourceIds = param.sourceIds,
            page = param.page,
            from = param.from
        )
    }
}
