package com.segunfamisa.zeitung.domain.getnews

import java.util.Date

data class NewsQueryParam(
    val sourceIds: List<String>,
    val page: Int = 0,
    val from: Date? = null
)
