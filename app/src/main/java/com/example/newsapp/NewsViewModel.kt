package com.example.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _articles = MutableLiveData<List<NewsArticle>>()
    val articles: LiveData<List<NewsArticle>> = _articles
    fun loadTopHeadlines(country: String) {
        viewModelScope.launch {
            try {
                val articles = repository.getTopHeadlines(country)
                _articles.postValue(articles)
            } catch (e: Exception) {
            }
        }
    }

    fun getTopHeadlines(s: String, category: String, apiKey: String) {

    }
}

private fun <T> MutableLiveData<T>.postValue(articles: NewsApiResponse) {

}
