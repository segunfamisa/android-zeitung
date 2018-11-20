package com.segunfamisa.zeitung.domain.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result

interface NewsSourcesRepository {

    suspend fun getNewsSources(category: String, language: String, country: String): Either<Error, Result<List<Source>>>
}