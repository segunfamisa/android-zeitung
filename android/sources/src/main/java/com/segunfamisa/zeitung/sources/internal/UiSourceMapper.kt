package com.segunfamisa.zeitung.sources.internal

import com.segunfamisa.zeitung.core.entities.Source
import javax.inject.Inject

internal class UiSourceMapper @Inject constructor() {

    fun from(input: List<Source>, followedChecker: (String) -> Boolean): List<UiItem> {
        return buildList {
            val sourcesByCategory = input.sortedBy { it.category }.groupBy { it.category }
            sourcesByCategory.forEach { (category, sources) ->
                add(UiItem.Section(name = category))
                addAll(
                    sources.map { source ->
                        UiItem.Source(
                            id = source.id,
                            name = source.name,
                            description = source.description,
                            url = source.url,
                            followed = followedChecker.invoke(source.id),
                            displayLabel = source.name.toLetterIconLabel()
                        )
                    }
                )
            }
        }
    }

    private fun String.toLetterIconLabel(): String {
        val parts = split(" ")
        return if (parts.size > 1) {
            "${parts[0][0]}${parts[1][0]}".uppercase()
        } else if (length > 1) {
            substring(0, 2).uppercase()
        } else if (length == 1) {
            this.uppercase()
        } else {
            ""
        }
    }

}
