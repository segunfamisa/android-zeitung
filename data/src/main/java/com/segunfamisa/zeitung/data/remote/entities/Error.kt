package com.segunfamisa.zeitung.data.remote.entities

import com.squareup.moshi.Json

data class Error(
    @field:Json(name = "code") val code: String,
    @field:Json(name = "message") val message: String
)
