package com.segunfamisa.zeitung.data.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.data.di.qualifiers.DataSource
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.newssources.NewsSourcesRepository
import javax.inject.Inject

internal class NewsSourcesRepositoryImpl @Inject constructor(
    @DataSource("remote") private val remoteSource: NewsSourcesDataSource
) : NewsSourcesRepository {

    override suspend fun getNewsSources(
        category: String,
        language: String,
        country: String
    ): Either<Error, List<Source>> {
        return remoteSource.getNewsSources(
            category = category,
            language = language,
            country = country
        )
    }
}
