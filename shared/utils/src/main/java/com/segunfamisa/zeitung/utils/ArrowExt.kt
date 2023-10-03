package com.segunfamisa.zeitung.utils

import arrow.core.Either
import arrow.core.orNull

/**
 * returns the Error (left) in the either if available, or null.
 */
fun <L, R> Either<L, R>.orError() = swap().orNull()
