package com.segunfamisa.zeitung.data.local.preferences

import com.google.protobuf.Timestamp
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.core.entities.Source
import java.util.Date


internal fun Article.toSavedArticle(): SavedArticle {
    val builder = SavedArticle.newBuilder()
        .setSource(source.toSavedSource())
        .setAuthor(author)
        .setTitle(title)
        .setDescription(description)
        .setUrl(url)
        .setImageUrl(imageUrl)
        .setContent(content)
        .setIsSaved(isSaved)

    if (publishedAt != null) {
        builder.setPublishedAt(
            Timestamp.newBuilder()
                .setSeconds(publishedAt!!.time / 1000L)
                .build()
        )
    }
    return builder.build()
}

internal fun List<SavedArticle>.toCoreArticleList(): List<Article> {
    return map {
        Article(
            source = it.source.toCoreSource(),
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            imageUrl = it.imageUrl,
            publishedAt = it.publishedAt?.seconds?.let { Date(it * 1000) },
            content = it.content,
            isSaved = it.isSaved
        )
    }
}

private fun SavedArticleSource.toCoreSource(): Source {
    return Source(
        id = this.id,
        name = this.name,
        description = this.description,
        url = this.url,
        category = this.category,
        language = this.language,
        country = this.country,
    )
}

private fun Source.toSavedSource(): SavedArticleSource {
    return SavedArticleSource.newBuilder()
        .setId(id)
        .setName(name)
        .setDescription(description)
        .setUrl(url)
        .setCategory(category)
        .setLanguage(language)
        .setCountry(country)
        .build()
}
