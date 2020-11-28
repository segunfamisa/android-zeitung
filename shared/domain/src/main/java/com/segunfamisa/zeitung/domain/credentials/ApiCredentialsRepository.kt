package com.segunfamisa.zeitung.domain.credentials

import arrow.core.Either
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow

interface ApiCredentialsRepository {

    fun saveToken(token: String): Flow<Either<Error, Unit>>
    fun getToken(): Flow<Either<Error, String>>
    fun getTokenSync(): String
}
