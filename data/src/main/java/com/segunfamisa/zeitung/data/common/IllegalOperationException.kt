package com.segunfamisa.zeitung.data.common

class IllegalOperationException(override val message: String) : Exception() {
    constructor() : this("")
}
