package com.segunfamisa.zeitung.data.remote.entities

import com.squareup.moshi.Json

data class Source(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "category") val category: String,
    @field:Json(name = "language") val language: String,
    @field:Json(name = "country") val country: String
)
