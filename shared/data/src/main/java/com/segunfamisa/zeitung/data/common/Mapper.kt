package com.segunfamisa.zeitung.data.common

/**
 * Interface to map from data layer to core layer
 */
internal interface Mapper<Data, Core> {
    fun from(data: Data): Core
}
