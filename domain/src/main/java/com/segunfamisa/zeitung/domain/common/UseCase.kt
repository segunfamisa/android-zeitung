package com.segunfamisa.zeitung.domain.common

import arrow.core.Either

abstract class UseCase<in Param, out Output> {

    abstract suspend operator fun invoke(param: Param): Either<Error, Result<out Output>>

    abstract fun cancel()
}
