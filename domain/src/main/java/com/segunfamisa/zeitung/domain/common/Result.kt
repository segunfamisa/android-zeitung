package com.segunfamisa.zeitung.domain.common

data class Error(val message: String, val throwable: Throwable? = null)
