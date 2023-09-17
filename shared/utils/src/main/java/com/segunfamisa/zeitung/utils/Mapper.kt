package com.segunfamisa.zeitung.utils

/**
 * Interface to map from one object to another.
 */
interface Mapper<From, To> {

    fun from(input: From): To
}
