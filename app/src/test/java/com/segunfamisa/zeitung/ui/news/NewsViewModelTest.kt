package com.segunfamisa.zeitung.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.nhaarman.mockitokotlin2.*
import com.segunfamisa.zeitung.CoroutinesMainDispatcherRule
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.core.entities.Source
import com.segunfamisa.zeitung.domain.headlines.GetHeadlinesUseCase
import com.segunfamisa.zeitung.domain.headlines.HeadlinesRepository
import com.segunfamisa.zeitung.util.DateFormatter
import com.segunfamisa.zeitung.util.DateFormatterImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    @Test
    fun `when headlines are fetched successfully, loading is shown at the beginning and hidden at the end`() =
        runBlocking {
            // given that the headlines are fetched successfully
            val articles = createArticles(5)
            val getHeadlinesUseCase = GetHeadlinesUseCase(repo = mock<HeadlinesRepository>().apply {
                whenever(getHeadlines(category = "technology", country = "us", sources = ""))
                    .thenReturn(Either.right(articles))
            })

            // and a date parser
            val dateFormatter = DateFormatterImpl()

            // and a view model
            val viewModel = createViewModel(getHeadlinesUseCase = getHeadlinesUseCase, dateFormatter = dateFormatter)

            // and we are observing the loading state
            val loadingObserver = mock<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            // when the call is made to get headlines
            viewModel.getHeadlines()

            // then verify that we show loading first and then hide it.
            val inOrder = inOrder(loadingObserver)
            inOrder.verify(loadingObserver).onChanged(true)
            inOrder.verify(loadingObserver).onChanged(false)
            inOrder.verifyNoMoreInteractions()
        }

    @Test
    fun `when headlines are fetched successfully, the article mapped ui articles list is emitted`() {
        // given that the headlines are fetched successfully
        val date = Date(1586131200000) //16/06/2019
        val articles = listOf(
            Article(
                source = Source(
                    id = "source1",
                    country = "country1",
                    language = "language1",
                    category = "category1",
                    url = "url1",
                    description = "description1",
                    name = "name1"
                ),
                author = "author1",
                title = "title1",
                description = "description1",
                url = "url1",
                imageUrl = "imageUrl1",
                publishedAt = date,
                content = "content1"
            ),
            Article(
                source = Source(
                    id = "source2",
                    country = "country2",
                    language = "language2",
                    category = "category2",
                    url = "url2",
                    description = "description2",
                    name = "name2"
                ),
                author = "author2",
                title = "title2",
                description = "description2",
                url = "url2",
                imageUrl = "imageUrl2",
                publishedAt = date,
                content = "content2"
            )
        )
        val getHeadlinesUseCase = GetHeadlinesUseCase(repo = mock<HeadlinesRepository> {
            onBlocking {
                getHeadlines(category = "technology", country = "us", sources = "")
            } doReturn Either.right(articles)
        })

        // and a date parser
        val stringDate = "16 June, 2019"
        val dateFormatter = mock<DateFormatter>().apply {
            whenever(this.format(date)).thenReturn(stringDate)
        }

        // and a view model
        val viewModel = createViewModel(getHeadlinesUseCase = getHeadlinesUseCase, dateFormatter = dateFormatter)

        // and we are observing the articles state
        val articlesObserver = mock<Observer<List<UiArticleItem>>>()
        viewModel.articles.observeForever(articlesObserver)

        // when the call is made to get headlines
        viewModel.getHeadlines()

        // then verify that we show the correct mapped articles
        verify(articlesObserver).onChanged(
            listOf(
                UiArticleItem(
                    source = "name1",
                    description = "description1",
                    imageUrl = "imageUrl1",
                    title = "title1",
                    url = "url1",
                    date = stringDate
                ),
                UiArticleItem(
                    source = "name2",
                    description = "description2",
                    imageUrl = "imageUrl2",
                    title = "title2",
                    url = "url2",
                    date = stringDate
                )
            )
        )
    }

    private fun createViewModel(
        getHeadlinesUseCase: GetHeadlinesUseCase,
        dateFormatter: DateFormatter
    ): NewsViewModel {
        return NewsViewModel(
            getHeadlinesUseCase = getHeadlinesUseCase,
            dateFormatter = dateFormatter
        )
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
