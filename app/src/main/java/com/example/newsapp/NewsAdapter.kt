package com.example.newsapp
import com.example.newsapp.databinding.ItemNewsBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(var articles: List<NewsArticle>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: NewsArticle) {
            binding.titleTextView.text = article.title
            binding.descriptionTextView.text = article.description
            binding.authorTextView.text = article.author
            binding.publishedAtTextView.text = article.publishedAt
            Glide.with(binding.root.context)
                .load(article.imageUrl)
                .into(binding.articleImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount() = articles.size
}
