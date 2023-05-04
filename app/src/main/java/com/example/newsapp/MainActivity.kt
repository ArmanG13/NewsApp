import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsViewModel
import com.example.newsapp.R
import okhttp3.Response

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsRecyclerView: RecyclerView

    companion object {
        private const val API_KEY = "your_API_key"
        private const val FILTER_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRecyclerView = findViewById(R.id.news_recycler_view)
        newsAdapter = NewsAdapter(this)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = newsAdapter

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        updateNewsList("general")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    private fun openFilterActivity() {
        val intent = Intent(this, FilterActivity::class.java)
        startActivityForResult(intent, FILTER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILTER_REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedCategory = data?.getStringExtra("selectedCategory") ?: "general"
            updateNewsList(selectedCategory)
        }
    }

    private fun updateNewsList(category: String) {
        viewModel.getTopHeadlines("us", category, API_KEY).observe(this) { response ->
            if (response.isSuccessful) {
                response.body()?.articles?.let { articles ->
                    newsAdapter.submitList(articles)
                }
            } else {
            }
        }
    }
    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            return networkInfo != null && networkInfo.isConnected
        }
    }
    private val _searchedNews = MutableLiveData<Response<>>()
    val searchedNews: LiveData<Response<>> = _searchedNews
    fun searchTopHeadlines(country: String, query: String, apiKey: String) {
        viewModelScope.launch {
            val response = newsApi.searchTopHeadlines(country, query, apiKey)
            _searchedNews.postValue(response)
        }
    }

    private fun searchNews(query: String) {
        if (isConnectedToInternet()) {
            viewModel.searchTopHeadlines("us", query, API_KEY).observe(this) { response ->
                if (response.isSuccessful) {
                    response.body()?.articles?.let { articles ->
                        if (articles.isEmpty()) {
                            showNoResultsFound()
                        } else {
                            newsAdapter.submitList(articles)
                        }
                    }
                } else {
                }
            }
        } else {
            val currentList = newsAdapter.currentList
            val filteredList = currentList.filter { article ->
                article.title.contains(query, ignoreCase = true)
            }
            if (filteredList.isEmpty()) {
                showNoResultsFound()
            } else {
                newsAdapter.submitList(filteredList)
            }
        }
    }

    private fun showNoResultsFound() {
        Toast.makeText(this, "No Results Found", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                openFilterActivity()
                true
            }
            R.id.action_search -> {
                val searchView = item.actionView as SearchView
                searchView.queryHint = "Search news"
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            searchNews(it)
                        }
                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
