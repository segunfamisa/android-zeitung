package com.segunfamisa.zeitung.domain.headlines

data class HeadlineQueryParam(
    val sources: List<String> = emptyList(),
    val category: String = "",
    val country: String = "",
    val page: Int = 0,
)
