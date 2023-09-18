package com.segunfamisa.zeitung.data.local.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.core.entities.defaultLanguage
import com.segunfamisa.zeitung.data.preferences.UserPreferencesSource
import com.segunfamisa.zeitung.domain.common.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserPreferencesSourceImpl @Inject constructor(
    private val dataStore: DataStore<Prefs>,
) : UserPreferencesSource {

    override val savedSources: Flow<List<String>> = dataStore.data
        .map { it.savedSourcesList.toList() }
        .distinctUntilChanged()

    override val language: Flow<String> = dataStore.data
        .map { it.languageWithFallback() }
        .distinctUntilChanged()

    override val savedArticles: Flow<List<Article>> = dataStore.data
        .map { it.savedArticlesList.toCoreArticleList() }

    override suspend fun saveSource(sourceId: String, saved: Boolean): Either<Error, Unit> {
        return try {
            val inserted = dataStore.updateData { prefs ->
                val sourceAlreadyExists = { id: String ->
                    prefs.savedSourcesList.any { it == id }
                }

                val savedSources = if (saved) {
                    // check if source already exists
                    if (sourceAlreadyExists(sourceId)) {
                        // remove it
                        prefs.savedSourcesList
                            .toMutableList().apply {
                                remove(sourceId)
                            }
                    } else {
                        prefs.savedSourcesList.toMutableList()
                    }.apply {
                        // then add the source
                        add(sourceId)
                    }
                } else {
                    if (sourceAlreadyExists(sourceId)) {
                        // remove it
                        prefs.savedSourcesList
                            .toMutableList()
                            .apply {
                                remove(sourceId)
                            }
                    } else {
                        prefs.savedSourcesList
                    }
                }
                prefs.toBuilder()
                    .setLanguage(prefs.languageWithFallback())
                    .clearSavedSources()
                    .addAllSavedSources(savedSources)
                    .build()
            }
            Log.d(LOG_TAG, "Source saved. All saved sources: ${inserted.savedSourcesList}")
            Either.right(Unit)
        } catch (exception: Exception) {
            Log.e(LOG_TAG, "Unable to save source", exception)
            Either.left(Error(message = "Unable to save source."))
        }
    }

    override suspend fun setLanguage(language: String): Either<Error, Unit> {
        return try {
            val saved = dataStore.updateData {
                it.toBuilder()
                    .setLanguage(language)
                    .build()
            }
            Log.d(LOG_TAG, "Language saved. ${saved.language}")
            Either.right(Unit)
        } catch (exception: Exception) {
            Either.left(Error(message = "Unable to save article."))
        }
    }

    override suspend fun saveArticle(article: Article, saved: Boolean): Either<Error, Unit> {
        return try {
            val inserted = dataStore.updateData { prefs ->
                val alreadyExists = { url: String ->
                    prefs.savedArticlesList
                        .any { it.url == url }
                }

                val savedArticles = if (saved) {
                    if (alreadyExists(article.url)) {
                        // remove existing one
                        prefs.savedArticlesList
                            .filterNot { it.url == article.url }
                            .toMutableList()
                    } else {
                        prefs.savedArticlesList.toMutableList()
                    }.apply {
                        // add this new entry
                        add(0, article.toSavedArticle())
                    }
                } else {
                    if (alreadyExists(article.url)) {
                        // remove it
                        prefs.savedArticlesList
                            .filterNot { it.url == article.url }
                            .toMutableList()
                    } else {
                        prefs.savedArticlesList.toMutableList()
                    }
                }

                prefs.toBuilder()
                    .setLanguage(prefs.languageWithFallback())
                    .clearSavedArticles()
                    .addAllSavedArticles(savedArticles)
                    .build()
            }
            Log.d(LOG_TAG, "Article saved. All saved articles: ${inserted.savedArticlesList.map { it.title }}")
            Either.right(Unit)
        } catch (exception: Exception) {
            Either.left(Error(message = "Unable to save article."))
        }
    }

    private fun Prefs.languageWithFallback(): String {
        return if (language.isNullOrEmpty()) {
            defaultLanguage()
        } else {
            language
        }
    }

    private companion object {
        private const val LOG_TAG = "UserPrefs"
    }
}
