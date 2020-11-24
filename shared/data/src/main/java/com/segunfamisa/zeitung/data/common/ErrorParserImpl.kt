package com.segunfamisa.zeitung.data.common

import com.segunfamisa.zeitung.domain.common.Error
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import javax.inject.Inject
import com.segunfamisa.zeitung.data.remote.entities.Error as ApiError

internal class ErrorParserImpl @Inject constructor(
    private val moshi: Moshi
) : ErrorParser {

    override fun parse(errorBody: ResponseBody): Error {
        return apiToDomain(
            apiError = moshi.adapter(ApiError::class.java).fromJson(errorBody.source())
        )
    }

    private fun apiToDomain(apiError: ApiError?): Error {
        return apiError?.let {
            Error(message = apiError.message, throwable = ApiException("${apiError.message}  ${apiError.code}"))
        } ?: Error(
            message = "Unable to parse",
            throwable = ParsingException()
        )
    }
}
