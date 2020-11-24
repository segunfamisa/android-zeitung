package com.segunfamisa.zeitung.domain.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.UseCase
import javax.inject.Inject

class GetNewsSourcesUseCase @Inject constructor(
    private val sourcesRepository: NewsSourcesRepository
) : UseCase<NewsSourcesQueryParam, List<Source>>() {

    override suspend fun invoke(param: NewsSourcesQueryParam): Either<Error, List<Source>> {
        return sourcesRepository.getNewsSources(category = param.category, language = param.language, country = "")
    }
}
