package com.segunfamisa.zeitung.news

import android.content.Context
import android.content.res.Resources
import com.segunfamisa.zeitung.common.getTimeAgo
import com.segunfamisa.zeitung.core.entities.Article
import java.util.Date
import javax.inject.Inject

class UiItemMapper @Inject constructor(
    private val context: Context,
) {
    fun createUiItem(article: Article): NewsUiItem {
        return NewsUiItem(
            headline = article.title,
            subhead = article.description,
            author = article.author,
            date = article.publishedAt?.asTimeAgo(resources = context.resources),
            url = article.url,
            saved = false,
            imageUrl = article.imageUrl,
            source = article.source.name,
        )
    }

    private fun Date.asTimeAgo(resources: Resources): String {
        return getTimeAgo(this.time, System.currentTimeMillis(), resources)
    }
}