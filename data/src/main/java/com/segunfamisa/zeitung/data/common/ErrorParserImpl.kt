package com.segunfamisa.zeitung.data.common

import com.segunfamisa.zeitung.domain.common.Error
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import javax.inject.Inject

internal class ErrorParserImpl @Inject constructor(
    private val moshi: Moshi
) : ErrorParser {

    override fun parse(errorBody: ResponseBody): Error {
        return moshi.adapter(Error::class.java)
            .fromJson(errorBody.source())
            ?: Error(
                message = "Unable to parse",
                throwable = ParsingException()
            )
    }
}
