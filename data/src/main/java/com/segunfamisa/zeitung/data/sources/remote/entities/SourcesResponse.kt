package com.segunfamisa.zeitung.data.sources.remote.entities

import com.squareup.moshi.Json

internal data class SourcesResponse(
    @field:Json(name = "sources") val sources: List<Source>
)
