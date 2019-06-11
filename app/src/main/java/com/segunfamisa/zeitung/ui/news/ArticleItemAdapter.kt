package com.segunfamisa.zeitung.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.core.entities.Article
import com.segunfamisa.zeitung.databinding.ItemArticleBinding
import javax.inject.Inject

class ArticleItemAdapter @Inject constructor() :
    RecyclerView.Adapter<ArticleItemAdapter.ArticleViewHolder>() {

    private var _items = listOf<Article>()
        set(value) {
            val callback = object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    field[oldItemPosition] == value[newItemPosition]

                override fun getOldListSize(): Int = field.size

                override fun getNewListSize(): Int = value.size

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    field[oldItemPosition] == value[newItemPosition]
            }
            val result = DiffUtil.calculateDiff(callback)
            field = value
            result.dispatchUpdatesTo(this)
        }

    fun setItems(items: List<Article>) {
        _items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = DataBindingUtil.inflate<ItemArticleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_article,
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.bind(_items[position])


    inner class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.executePendingBindings()
        }

        fun bind(article: Article) {
            with(article) {
                binding.articleDescription.text = description
                binding.articleDescription.isGone = description.isEmpty()

                binding.articleSourceText.text = source.name
                binding.articleTitle.text = title
                binding.articleTime.text = article.publishedAt.toString() // temp

                binding.articleImage.isGone = imageUrl.isEmpty()
                Glide.with(binding.articleImage)
                    .load(imageUrl)
                    .into(binding.articleImage)
            }
        }
    }
}