package nl.bijdorpstudio.feature.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.Group
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

        val contentViews = findViewById<Group>(R.id.content_views)

        val recyclerView = findViewById<FlexRecyclerView>(R.id.recycler_view)
        val searchView = findViewById<SearchView>(R.id.search_view)

        val model = ViewModelProvider(
            this as ViewModelStoreOwner,
            SearchViewModelFactory(DI.navigator!!)
        ).get(SearchViewModel::class.java)

        model.contentData.observe(
            this,
            Observer {
                val progressView = findViewById<ContentLoadingProgressBar>(R.id.progress_bar)

                when (it) {
                    is Content.Result -> {
                        progressView.hide()
                        contentViews.visibility = View.VISIBLE

                        recyclerView.setItems(it.items)
                    }
                    is Content.Error -> {
                        progressView.hide()
                    }
                    is Content.Loading -> {
                        progressView.show()
                        contentViews.visibility = View.GONE
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
}

internal class SearchViewModelFactory(private val navigator: Navigator) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiKey = DI.apiKey!!
        val provider = RetrofitProvider(ApiKeyAppendInterceptor(apiKey))
        val baseUrl = Url.of("https://api.nytimes.com/")!!
        val retrofit = provider.createRetrofit(baseUrl, BuildConfig.DEBUG)
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(ArticleSearchClientImpl(retrofit), navigator) as T
    }
}
