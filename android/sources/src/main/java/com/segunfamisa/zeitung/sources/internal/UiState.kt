package com.segunfamisa.zeitung.sources.internal

internal data class UiState(
    val loading: Boolean,
    val sources: List<UiItem> = emptyList(),
)

internal sealed interface UiItem {

    val name: String

    data class Source(
        val id: String,
        override val name: String,
        val description: String,
        val url: String,
        val followed: Boolean,
    ) : UiItem

    data class Section(override val name: String) : UiItem
}