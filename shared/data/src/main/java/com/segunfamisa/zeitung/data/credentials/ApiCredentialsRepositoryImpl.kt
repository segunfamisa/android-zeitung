package com.segunfamisa.zeitung.data.credentials

import arrow.core.Either
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.credentials.ApiCredentialsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * Temporary in-memory API token store
 */
class ApiCredentialsRepositoryImpl @Inject constructor() : ApiCredentialsRepository {
    var token = ""
    override fun saveToken(token: String): Flow<Either<Error, Unit>> {
        this.token = token
        return flowOf(Either.right(Unit))
    }

    override fun getToken(): Flow<Either<Error, String>> {
        return flowOf(Either.right(token))
    }

    override fun getTokenSync(): String {
        return token
    }
}
