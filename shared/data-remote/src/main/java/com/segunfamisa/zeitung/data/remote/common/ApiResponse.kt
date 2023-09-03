package com.segunfamisa.zeitung.data.remote.common

internal sealed class ApiResponse<out T> {
    data class Success<T>(val entity: T) : ApiResponse<T>()
    data class Error<T>(val throwable: Throwable) : ApiResponse<T>()
}

internal sealed class ApiCallError(override val message: String) : RuntimeException() {

    /**
     * Server-side API call error, basically, non 2xx/3xx status codes.
     */
    data class ServerError(val errorCode: Int, override val message: String) : ApiCallError(message)

    /**
     * Client-side API call error related to the network, e.g, due to I/O timeout, etc
     */
    data class ClientNetworkError(override val message: String) : ApiCallError(message)

    /**
     * Unknown error that is unusual.
     */
    data class UnknownApiCallError(override val message: String) : ApiCallError(message)

}
