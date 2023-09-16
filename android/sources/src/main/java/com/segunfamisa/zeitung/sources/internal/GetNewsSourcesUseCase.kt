package com.segunfamisa.zeitung.sources.internal

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.FlowUseCase
import com.segunfamisa.zeitung.domain.newssources.NewsSourcesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetNewsSourcesUseCase @Inject constructor(
    private val sourcesRepository: NewsSourcesRepository
) : FlowUseCase<SourcesQueryParam, List<Source>>() {

    override fun execute(param: SourcesQueryParam): Flow<Either<Error, List<Source>>> {
        return sourcesRepository.getNewsSources(
            category = "",
            language = param.language,
            country = ""
        )
    }
}
