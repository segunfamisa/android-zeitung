package com.segunfamisa.zeitung.data.local.preferences

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.data.preferences.UserPreferencesSource
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

internal class UserPreferencesSourceImpl : UserPreferencesSource {

    override val savedSources: Flow<List<String>> = emptyFlow()
    override val language: Flow<String> = flowOf("en")
    override val savedArticles: Flow<List<Article>> = emptyFlow()

    override suspend fun saveSource(sourceId: String): Either<Error, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun setLanguage(language: String): Either<Error, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun saveArticle(article: Article): Either<Error, Unit> {
        TODO("Not yet implemented")
    }
}
