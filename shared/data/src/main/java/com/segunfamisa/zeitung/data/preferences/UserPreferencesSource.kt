package com.segunfamisa.zeitung.data.preferences

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow

interface UserPreferencesSource {

    val savedSources: Flow<List<String>>
    val language: Flow<String>
    val savedArticles: Flow<List<Article>>

    suspend fun saveSource(sourceId: String): Either<Error, Unit>
    suspend fun setLanguage(language: String): Either<Error, Unit>
    suspend fun saveArticle(article: Article): Either<Error, Unit>
}
