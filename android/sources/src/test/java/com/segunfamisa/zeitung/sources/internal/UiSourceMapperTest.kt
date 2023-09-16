package com.segunfamisa.zeitung.sources.internal

import com.segunfamisa.zeitung.core.entities.Source
import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class UiSourceMapperTest {

    private val mapper = UiSourceMapper()

    @Test
    fun `list of sources is mapped to a list of source items`() {
        // Given the input list of sources with two categories
        val input = listOf(
            createSource(id = "bbc", category = "entertainment"),
            createSource(id = "zdf", category = "general"),
            createSource(id = "cnn", category = "entertainment"),
        )

        // When we map the input
        val uiItems = mapper.from(input = input, followedChecker = { false })

        // Then verify that the source items are grouped accordingly
        val expectedUiItems = listOf(
            UiItem.Section("entertainment"),
            createUiSource(id = "bbc", followed = false),
            createUiSource(id = "cnn", followed = false),
            UiItem.Section("general"),
            createUiSource(id = "zdf", followed = false),
        )
        assertEquals(expectedUiItems, uiItems)
    }

    @Test
    fun `list of sources containing single source is mapped to one source item with one section`() {
        // Given the input list of one source
        val input = listOf(
            createSource(id = "bbc", category = "entertainment"),
        )

        // When we map the input
        val uiItems = mapper.from(input = input, followedChecker = { true })

        // Then verify that the source items are grouped accordingly
        val expectedUiItems = listOf(
            UiItem.Section("entertainment"),
            createUiSource(id = "bbc", followed = true),
        )
        assertEquals(expectedUiItems, uiItems)
    }

    private fun createSource(id: String, category: String): Source {
        return Source(
            id = id,
            country = "country $id",
            language = "en",
            category = category,
            url = "url",
            description = "description",
            name = "name $id"
        )
    }

    private fun createUiSource(id: String, followed: Boolean): UiItem.Source {
        return UiItem.Source(
            id = id,
            name = "name $id",
            description = "description",
            url = "url",
            followed = followed
        )
    }
}
