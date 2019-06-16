package com.segunfamisa.zeitung.data.newssources

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.data.remote.ApiService
import com.segunfamisa.zeitung.domain.common.Error
import javax.inject.Inject

internal class RemoteNewsSourcesDataSource @Inject constructor(
    private val apiService: ApiService,
    private val mapper: SourcesMapper
) : NewsSourcesDataSource {

    override suspend fun getNewsSources(
        category: String,
        language: String,
        country: String
    ): Either<Error, List<Source>> {
        val response =
            apiService
                .getSources(category = category, language = language, country = country)

        return if (response.body() != null) {
            val sources = response.body()!!.sources.map { apiSource ->
                mapper.from(data = apiSource)
            }
            Either.Right(sources)
        } else {
            // TODO map api error into actual domain error type
            Either.Left(Error(message = "Unable to get sources"))
        }
    }
}
