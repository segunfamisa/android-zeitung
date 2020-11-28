package com.segunfamisa.zeitung.domain.credentials

import arrow.core.Either
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveApiCredentialsUseCase @Inject constructor(
    private val apiCredentialsRepository: ApiCredentialsRepository
) : FlowUseCase<String, Unit>() {

    override fun execute(param: String): Flow<Either<Error, Unit>> {
        return apiCredentialsRepository.saveToken(param)
    }
}
