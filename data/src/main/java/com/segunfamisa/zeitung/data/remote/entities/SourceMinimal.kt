package com.segunfamisa.zeitung.data.remote.entities

import com.squareup.moshi.Json

/**
 * Minimal version of [Source]
 */
internal data class SourceMinimal(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?
) {
    fun toSource(): Source =
        Source(
            id = id ?: "",
            name = name ?: "",
            description = "",
            category = "",
            url = "",
            language = "",
            country = ""
        )
}
