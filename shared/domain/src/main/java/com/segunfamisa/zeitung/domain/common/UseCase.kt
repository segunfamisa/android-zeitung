package com.segunfamisa.zeitung.domain.common

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

abstract class UseCase<in Param, out Output> {

    abstract suspend operator fun invoke(param: Param): Either<Error, Output>
}

abstract class FlowUseCase<in Param, out Result> {

    abstract fun execute(param: Param): Flow<Either<Error, Result>>
}
