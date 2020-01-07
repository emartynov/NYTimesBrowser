package nl.bijdorpstudio.feature.search

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import nl.bijdorpstudio.common.data.Url
import nl.bijdorpstudio.common.navigation.Navigator
import nl.bijdorpstudio.common.search.ArticleSearchClientImpl
import nl.bijdorpstudio.lib.flex.FlexRecyclerView
import nl.bijdorpstudio.lib.retrofit.ApiKeyAppendInterceptor
import nl.bijdorpstudio.lib.retrofit.RetrofitProvider

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<FlexRecyclerView>(R.id.recycler_view)
        val searchView = findViewById<SearchView>(R.id.search_view)
        val progressView = findViewById<ContentLoadingProgressBar>(R.id.progress_bar)

        val model = ViewModelProvider(
            this as ViewModelStoreOwner,
            SearchViewModelFactory(DI.navigator!!)
        ).get(SearchViewModel::class.java)

        model.contentData.observe(
            this,
            Observer {

                when (it) {
                    is Content.Result -> {
                        progressView.hide()
                        recyclerView.setItems(it.items)
                    }
                    is Content.Error -> {
                        progressView.hide()
                        showGenericError()
                    }
                    is Content.Loading -> {
                        progressView.show()
                    }
                }
            }
        )

        val listener = PaginationScrollListener(recyclerView, model::loadNext)
        recyclerView.addOnScrollListener(listener)

        searchView.setOnQueryTextListener(
            object : SearchViewTextListenerAdapter() {
                override fun onQueryTextSubmit(query: String): Boolean {
                    model.search(query)
                    return true
                }
            }
        )

        searchView.setOnCloseListener {
            model.search("")
            false
        }

        if (savedInstanceState == null) {
            model.refresh()
        }
    }

    private fun showGenericError() {
        AlertDialog.Builder(this)
            .setTitle(R.string.error_title)
            .setMessage(R.string.error_message)
            .setPositiveButton(R.string.error_button, null)
            .show()
    }
}

internal class SearchViewModelFactory(private val navigator: Navigator) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiKey = DI.apiKey!!
        val provider = RetrofitProvider(ApiKeyAppendInterceptor(apiKey))
        val baseUrl = Url.of("https://api.nytimes.com/")!!
        val retrofit = provider.createRetrofit(baseUrl, BuildConfig.DEBUG)
        val favouriteStorage = FavouriteStorage()
        val repository = ArticleRepository(ArticleSearchClientImpl(retrofit), favouriteStorage)
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(repository, navigator) as T
    }
}
