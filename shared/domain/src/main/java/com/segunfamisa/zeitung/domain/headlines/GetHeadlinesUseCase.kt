package com.segunfamisa.zeitung.domain.headlines

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeadlinesUseCase @Inject constructor(
    private val repo: HeadlinesRepository
) : FlowUseCase<HeadlineQueryParam, List<Article>>() {

    override fun execute(param: HeadlineQueryParam): Flow<Either<Error, List<Article>>> {
        return repo.getHeadlines(
            category = param.category,
            country = param.country,
            sources = param.sources.joinToString(separator = ","),
            page = param.page
        )
    }
}
