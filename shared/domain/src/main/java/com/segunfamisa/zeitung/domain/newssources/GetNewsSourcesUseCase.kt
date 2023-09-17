package com.segunfamisa.zeitung.domain.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsSourcesUseCase @Inject constructor(
    private val sourcesRepository: NewsSourcesRepository
)  {

    fun execute(param: SourcesQueryParam): Flow<Either<Error, List<Source>>> {
        return sourcesRepository.getNewsSources(
            category = "",
            language = param.language,
            country = ""
        )
    }
}
