package nl.bijdorpstudio.feature.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import nl.bijdorpstudio.common.search.ArticleSearchClientImpl
import nl.bijdorpstudio.feature.search.flex.FlexRecyclerView
import nl.bijdorpstudio.lib.retrofit.ApiKey
import nl.bijdorpstudio.lib.retrofit.ApiKeyAppendInterceptor
import nl.bijdorpstudio.lib.retrofit.RetrofitProvider
import java.net.URL

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<FlexRecyclerView>(R.id.recycler_view)

        val model = ViewModelProvider(
            this as ViewModelStoreOwner,
            SearchViewModelFactory()
        ).get(SearchViewModel::class.java)

        model.contentDate.observe(
            this,
            Observer {
                val progressView = findViewById<ContentLoadingProgressBar>(R.id.progress_bar)

                when (it) {
                    is Content.Result -> {
                        progressView.hide()
                        recyclerView.visibility = View.VISIBLE

                        recyclerView.setItems(it.items)
                    }
                    is Content.Error -> {
                        progressView.hide()
                    }
                    is Content.Loading -> {
                        progressView.show()
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        )

        if (savedInstanceState == null) {
            model.refresh()
        }
    }
}

//TODO: DI
internal class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val apiKey = ApiKey.of("***REMOVED***") as ApiKey
        val baseUrl = URL("https://api.nytimes.com/")
        val provider = RetrofitProvider(ApiKeyAppendInterceptor(apiKey))
        val retrofit = provider.createRetrofit(baseUrl, BuildConfig.DEBUG)
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(ArticleSearchClientImpl(retrofit)) as T
    }
}
