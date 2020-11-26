package com.segunfamisa.zeitung.domain.getnews

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) : FlowUseCase<NewsQueryParam, List<Article>>() {

    override fun execute(param: NewsQueryParam): Flow<Either<Error, List<Article>>> {
        return repository.getNews(
            sourceIds = param.sourceIds,
            page = param.page,
            from = param.from
        )
    }
}
