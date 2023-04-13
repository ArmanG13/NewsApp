package com.example.newsapp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String? = null,
        @Query("q") query: String? = null
    ): NewsApiResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String = "publishedAt"
    ): NewsApiResponse
}

class NewsApiClient {
    private val apiService: NewsApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(NewsApiService::class.java)
    }

    suspend fun getTopHeadlines() = apiService.getTopHeadlines()

    suspend fun searchNews(query: String) = apiService.searchNews(query)
}
