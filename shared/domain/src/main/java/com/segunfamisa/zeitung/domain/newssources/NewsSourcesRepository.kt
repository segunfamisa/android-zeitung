package com.segunfamisa.zeitung.domain.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow

interface NewsSourcesRepository {

    fun getNewsSources(category: String, language: String, country: String): Flow<Either<Error, List<Source>>>
}