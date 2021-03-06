package com.segunfamisa.zeitung.data.remote.common

import com.segunfamisa.zeitung.domain.common.Error
import okhttp3.ResponseBody

internal interface ErrorParser {

    fun parse(errorBody: ResponseBody): Error
}
