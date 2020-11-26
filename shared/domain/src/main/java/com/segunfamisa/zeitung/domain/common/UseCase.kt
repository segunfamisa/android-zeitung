package com.segunfamisa.zeitung.domain.common

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in Param, out Result> {

    abstract fun execute(param: Param): Flow<Either<Error, Result>>
}
