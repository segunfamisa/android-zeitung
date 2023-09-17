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
                            followed = followedChecker.invoke(source.id)
                        )
                    }
                )
            }
        }
    }

}
