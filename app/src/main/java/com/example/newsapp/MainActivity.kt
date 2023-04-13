package com.example.newsapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = NewsAdapter(emptyList())
        binding.newsRecyclerView.adapter = adapter

        viewModel.getTopHeadlines()

        viewModel.articles.observe(this, { articles ->
            adapter.articles = articles
            adapter.notifyDataSetChanged()
        })

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchNews(query)
            }
        }
    }
}
