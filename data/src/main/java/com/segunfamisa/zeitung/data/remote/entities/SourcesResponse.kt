package com.segunfamisa.zeitung.data.remote.entities

import com.squareup.moshi.Json

data class SourcesResponse(
    @field:Json(name = "sources") val sources: List<Source>
)
