package com.segunfamisa.zeitung.data.sources.remote

import com.segunfamisa.zeitung.data.sources.remote.entities.Article
import com.segunfamisa.zeitung.data.sources.remote.entities.SourceMinimal
import java.util.Date

internal object TestDataGenerator {

    fun createArticles(count: Int): List<Article> {
        val articles = mutableListOf<Article>()
        repeat(count) {
            articles.add(
                Article(
                    source = SourceMinimal(
                        id = "source $it",
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
