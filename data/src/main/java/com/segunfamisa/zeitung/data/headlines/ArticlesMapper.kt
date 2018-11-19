package com.segunfamisa.zeitung.data.headlines

import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.data.common.Mapper
import javax.inject.Inject
import com.segunfamisa.zeitung.core.entities.Article as CoreArticle
import com.segunfamisa.zeitung.data.remote.entities.Article as RemoteArticle

internal class ArticlesMapper @Inject constructor() :
    Mapper<RemoteArticle, CoreArticle> {

    override fun from(data: RemoteArticle): CoreArticle {
        return CoreArticle(
            source = Source(
                id = data.source.id,
                name = data.source.name,
                description = "",
                language = "",
                url = "",
                category = "",
                country = ""
            ),
            url = data.url,
            description = data.description,
            author = data.author ?: "",
            title = data.title,
            imageUrl = data.imageUrl,
            publishedAt = data.publishedAt,
            content = data.content
        )
    }
}
