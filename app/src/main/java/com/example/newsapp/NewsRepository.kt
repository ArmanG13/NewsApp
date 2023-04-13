package com.example.newsapp

import retrofit2.Retrofit
import retrofit2.Response

import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository {
    private val apiService = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApiService::class.java)

    suspend fun getTopHeadlines(country: String): List<NewsArticle> {
        val response = apiService.getTopHeadlines(country)
        if (response.isSuccessful) {
            return response.body()?.articles ?: emptyList()
        } else {
            throw Exception("Failed to fetch top headlines")
        }
    }
}
