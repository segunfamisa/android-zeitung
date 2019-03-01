package com.segunfamisa.zeitung.domain.news

import java.util.Date

data class NewsQueryParam(
    val sourceIds: List<String>,
    val page: Int = 0,
    val from: Date? = null
)
