package com.segunfamisa.zeitung.data.preferences

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataSource: UserPreferencesSource,
) : UserPreferencesRepository {

    override val savedSources: Flow<List<String>> = dataSource.savedSources
    override val language: Flow<String> = dataSource.language
    override val savedArticles: Flow<List<Article>> = dataSource.savedArticles

    override suspend fun saveSource(sourceId: String): Either<Error, Unit> {
        return dataSource.saveSource(sourceId)
    }

    override suspend fun setLanguage(language: String): Either<Error, Unit> {
        return dataSource.setLanguage(language)
    }

    override suspend fun saveArticle(article: Article): Either<Error, Unit> {
        return dataSource.saveArticle(article)
    }
}
