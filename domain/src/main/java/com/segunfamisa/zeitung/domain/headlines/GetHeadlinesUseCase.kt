package com.segunfamisa.zeitung.domain.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.UseCase
import javax.inject.Inject

class GetHeadlinesUseCase @Inject constructor(
    private val repo: HeadlinesRepository
) : UseCase<HeadlineQueryParam, List<Article>>() {

    override suspend fun invoke(param: HeadlineQueryParam): Either<Error, List<Article>> {
        return repo.getHeadlines(category = param.category, country = param.country, sources = "")
    }
}
