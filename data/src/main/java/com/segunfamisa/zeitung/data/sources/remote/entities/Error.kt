package com.segunfamisa.zeitung.data.sources.remote.entities

import com.squareup.moshi.Json

internal data class Error(
    @field:Json(name = "code") val code: String,
    @field:Json(name = "message") val message: String
)
