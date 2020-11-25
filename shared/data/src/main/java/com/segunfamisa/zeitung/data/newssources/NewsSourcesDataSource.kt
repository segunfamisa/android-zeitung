package com.segunfamisa.zeitung.data.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error

/**
 * Interface to get news sources
 */
interface NewsSourcesDataSource {

    suspend fun getNewsSources(category: String, language: String, country: String): Either<Error, List<Source>>
}
