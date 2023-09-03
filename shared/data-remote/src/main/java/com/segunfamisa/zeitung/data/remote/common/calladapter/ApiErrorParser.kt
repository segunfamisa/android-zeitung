package com.segunfamisa.zeitung.data.remote.common.calladapter

import com.segunfamisa.zeitung.data.remote.entities.Error
import com.squareup.moshi.Moshi
import javax.inject.Inject

internal interface ApiErrorParser {

    fun parse(jsonString: String?): String?
}

internal class ApiErrorParserImpl @Inject constructor(private val moshi: Moshi) : ApiErrorParser {

    override fun parse(jsonString: String?): String? {
        return jsonString?.let {
            moshi.adapter(Error::class.java).fromJson(jsonString)
        }?.message
    }

}