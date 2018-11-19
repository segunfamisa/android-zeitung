package com.segunfamisa.zeitung.domain.headlines

import arrow.core.Either
import arrow.core.orNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.common.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class GetHeadlinesUseCaseTest {

    private val repo = mock<HeadlinesRepository>()
    private lateinit var useCase: GetHeadlinesUseCase

    @Before
    fun setUp() {
        useCase = GetHeadlinesUseCase(repo = repo)
    }

    @Test
    fun `get headlines successfully`() = runBlocking {
        // given that we request business headlines
        val category = "business"

        // given that the repository returns news for that response
        val articles = createArticles(count = 2)
        whenever(repo.getHeadlines(category = category, sources = "", country = "")).thenReturn(
            Either.right(
                Result(
                    articles
                )
            )
        )

        // when we invoke the use case
        val result = useCase.invoke(param = HeadlineQueryParam(category = category))

        // then we assert that the retrieved articles match the ones returned from the repository
        val retrievedArticles = result.orNull()!!.data
        assertEquals(2, retrievedArticles.size)
        assertEquals(articles, retrievedArticles)
    }

    private fun createArticles(count: Int): List<Article> {
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
