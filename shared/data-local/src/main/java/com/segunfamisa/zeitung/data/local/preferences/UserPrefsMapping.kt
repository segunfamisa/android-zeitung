package com.segunfamisa.zeitung.data.local.preferences

import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.core.entities.Source
import java.text.SimpleDateFormat
import java.util.Locale

const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX" // "2023-09-17T04:01:16+00:00"
private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)

internal fun Article.toSavedArticle(): SavedArticle {
    val builder = SavedArticle.newBuilder()
        .setSource(source.toSavedSource())
        .setAuthor(author)
        .setTitle(title)
        .setUrl(url)
        .setImageUrl(imageUrl)
        .setContent(content)
        .setIsSaved(isSaved)

    description?.let { builder.setDescription(description) }
    publishedAt?.let { builder.setPublishedAt(dateFormat.format(it)) }
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
            publishedAt = it.publishedAt?.let { dateString ->
                if (dateString.isNotEmpty()) {
                    dateFormat.parse(dateString)
                } else {
                    null
                }
            },
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
