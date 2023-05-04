package com.example.newsapp

import com.example.newsapp.NewsApiResponse
import com.example.newsapp.NewsApiService


class NewsRepository(private val newsApiService: NewsApiService) {

    suspend fun getTopHeadlines(category: String, query: String): NewsApiResponse {
        return newsApiService.getTopHeadlines("us", category, query)
    }
}
