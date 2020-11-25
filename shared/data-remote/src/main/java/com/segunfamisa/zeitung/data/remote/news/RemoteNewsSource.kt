package com.segunfamisa.zeitung.data.remote.news

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.data.news.NewsSource
import com.segunfamisa.zeitung.data.remote.service.ApiService
import com.segunfamisa.zeitung.data.remote.common.ErrorParser
import com.segunfamisa.zeitung.data.remote.common.nullify
import com.segunfamisa.zeitung.domain.common.Error
import java.util.Date
import javax.inject.Inject
import com.segunfamisa.zeitung.core.entities.Article as CoreArticle

internal class RemoteNewsSource @Inject constructor(
    private val apiService: ApiService,
    private val errorParser: ErrorParser
) : NewsSource {

    override suspend fun getNews(
        sources: String,
        page: Int,
        from: Date?
    ): Either<Error, List<CoreArticle>> {
        val response = apiService.getNews(
            sources = sources.nullify(),
            page = page
        )

        return response.body()
            ?.let { res ->
                val articles = res.articles.map { apiArticle ->
                    CoreArticle(
                        source = Source(
                            id = apiArticle.source.id ?: "",
                            name = apiArticle.source.name ?: "",
                            description = "",
                            language = "",
                            url = "",
                            category = "",
                            country = ""
                        ),
                        url = apiArticle.url,
                        description = apiArticle.description ?: "",
                        author = apiArticle.author ?: "",
                        title = apiArticle.title,
                        imageUrl = apiArticle.imageUrl ?: "",
                        publishedAt = apiArticle.publishedAt,
                        content = apiArticle.content ?: "",
                        isSaved = false
                    )
                }
                Either.right(articles)
            }
            ?: response.errorBody()?.let { error ->
                Either.left(errorParser.parse(error))
            } ?: Either.left(Error("Unable to fetch data", Throwable("")))
    }
}
