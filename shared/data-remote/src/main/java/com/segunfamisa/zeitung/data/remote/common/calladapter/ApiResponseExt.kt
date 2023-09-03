package com.segunfamisa.zeitung.data.remote.common.calladapter

import com.segunfamisa.zeitung.data.remote.common.ApiCallError
import com.segunfamisa.zeitung.data.remote.common.ApiResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type


internal fun <T : Any?> Response<T>.toApiResponse(
    paramType: Type, parser: ApiErrorParser? = null
): ApiResponse<T?> {
    if (isSuccessful) {
        if (paramType == Unit::class.java) {
            return ApiResponse.Success(entity = Unit as? T)
        }
        return ApiResponse.Success(entity = body())
    } else {
        val apiCallError = parser?.parse(errorBody()?.string())?.let {
            ApiCallError.ServerError(errorCode = code(), it)
        } ?: ApiCallError.UnknownApiCallError("Unknown error: ${errorBody()?.string()}")

        return ApiResponse.Error(throwable = apiCallError)
    }
}

internal fun Exception.toApiCallError(): ApiCallError {
    return when (this) {
        is HttpException -> ApiCallError.ServerError(
            errorCode = this.code(), message = this.message()
        )

        is IOException -> ApiCallError.ClientNetworkError(
            message = this.message ?: ""
        )

        else -> ApiCallError.UnknownApiCallError(message = this.message ?: "")
    }
}