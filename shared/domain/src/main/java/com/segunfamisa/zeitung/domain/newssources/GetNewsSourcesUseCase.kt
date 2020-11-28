package com.segunfamisa.zeitung.domain.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsSourcesUseCase @Inject constructor(
    private val sourcesRepository: NewsSourcesRepository
) : FlowUseCase<NewsSourcesQueryParam, List<Source>>() {

    override fun execute(param: NewsSourcesQueryParam): Flow<Either<Error, List<Source>>> {
        return sourcesRepository.getNewsSources(category = param.category, language = param.language, country = "")
    }
}
