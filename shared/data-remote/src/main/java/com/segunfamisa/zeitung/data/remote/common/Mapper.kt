package com.segunfamisa.zeitung.data.remote.common

/**
 * Interface to map from data layer to core layer
 */
internal interface Mapper<Data, Core> {
    fun from(data: Data): Core
}
