package com.segunfamisa.zeitung.domain.common

data class Result<T>(val data: T)
data class Error(val message: String, val throwable: Throwable? = null)
