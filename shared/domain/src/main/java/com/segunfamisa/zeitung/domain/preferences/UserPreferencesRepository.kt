package com.segunfamisa.zeitung.domain.preferences

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val savedSources: Flow<List<String>>
    val language: Flow<String>
    val savedArticles: Flow<List<Article>>
    suspend fun saveSource(sourceId: String, saved: Boolean): Either<Error, Unit>
    suspend fun setLanguage(language: String): Either<Error, Unit>
    suspend fun saveArticle(article: Article, saved: Boolean): Either<Error, Unit>

}
