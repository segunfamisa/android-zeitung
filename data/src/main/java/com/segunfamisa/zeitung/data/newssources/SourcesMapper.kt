package com.segunfamisa.zeitung.data.newssources

import com.segunfamisa.zeitung.data.common.Mapper
import javax.inject.Inject
import com.segunfamisa.zeitung.core.entities.Source as CoreNewsSource
import com.segunfamisa.zeitung.data.sources.remote.entities.Source as DataNewsSource

internal class SourcesMapper @Inject constructor() : Mapper<DataNewsSource, CoreNewsSource> {

    override fun from(data: DataNewsSource): CoreNewsSource {
        return CoreNewsSource(
            id = data.id,
            name = data.name,
            url = data.url,
            description = data.description,
            category = data.category,
            country = data.country,
            language = data.language
        )
    }
}
