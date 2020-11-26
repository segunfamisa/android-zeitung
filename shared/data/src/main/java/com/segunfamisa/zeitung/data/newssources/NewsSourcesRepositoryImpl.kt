package com.segunfamisa.zeitung.data.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.data.di.qualifiers.Remote
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.newssources.NewsSourcesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class NewsSourcesRepositoryImpl @Inject constructor(
    @Remote private val remoteSource: NewsSourcesDataSource
) : NewsSourcesRepository {

    override fun getNewsSources(
        category: String,
        language: String,
        country: String
    ): Flow<Either<Error, List<Source>>> {
        return flow {
            emit(
                remoteSource.getNewsSources(
                    category = category,
                    language = language,
                    country = country
                )
            )
        }
    }
}
