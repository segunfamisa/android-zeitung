package com.segunfamisa.zeitung.domain.utils

import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.core.entities.Source
import java.util.Date

object ArticleCreator {

    fun createArticles(count: Int): List<Article> {
        val articles = mutableListOf<Article>()
        repeat(count) {
            articles.add(
                Article(
                    source = Source(
                        id = "source $it",
                        country = "country $it",
                        language = "language $it",
                        category = "category $it",
                        url = "url $it",
                        description = "description $it",
                        name = "name $it"
                    ),
                    author = "author $it",
                    title = "title $it",
                    description = "description $it",
                    url = "url $it",
                    imageUrl = "imageUrl $it",
                    publishedAt = Date(System.currentTimeMillis() + count),
                    content = "content $it"
                )
            )
        }

        return articles
    }
}
