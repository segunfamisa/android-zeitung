package com.segunfamisa.zeitung.data.remote.news

import arrow.core.Either
import com.segunfamisa.zeitung.data.news.NewsSource
import com.segunfamisa.zeitung.data.remote.common.ApiResponse
import com.segunfamisa.zeitung.data.remote.common.nullify
import com.segunfamisa.zeitung.data.remote.entities.Article
import com.segunfamisa.zeitung.data.remote.headlines.ArticlesMapper
import com.segunfamisa.zeitung.data.remote.service.ApiService
import com.segunfamisa.zeitung.domain.common.Error
import java.util.Date
import javax.inject.Inject
import com.segunfamisa.zeitung.core.entities.Article as CoreArticle

internal class RemoteNewsSource @Inject constructor(
    private val apiService: ApiService,
    private val articlesMapper: ArticlesMapper,
) : NewsSource {

    override suspend fun getNews(
        sources: String, page: Int, from: Date?
    ): Either<Error, List<CoreArticle>> {
        val response = apiService.getNews(
            sources = sources.nullify(), page = page
        )

        return when (response) {
            is ApiResponse.Success -> Either.right(response.entity.articles.toCoreArticles())
            is ApiResponse.Error -> Either.left(
                Error(message = response.throwable.message ?: "", throwable = response.throwable)
            )
        }
    }

    private fun List<Article>.toCoreArticles(): List<CoreArticle> {
        return this.map { apiArticle ->
            articlesMapper.from(apiArticle)
        }
    }
}
