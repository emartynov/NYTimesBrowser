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
import nl.bijdorpstudio.lib.retrofit.ApiKeyAppendInterceptor
import nl.bijdorpstudio.lib.retrofit.RetrofitProvider

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model = ViewModelProvider(
            this as ViewModelStoreOwner,
            SearchViewModelFactory()
        ).get(SearchViewModel::class.java)

        model.contentDate.observe(
            this,
            Observer {
                val recyclerView = findViewById<FlexRecyclerView>(R.id.recycler_view)
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

internal class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val provider = RetrofitProvider(ApiKeyAppendInterceptor("***REMOVED***"))
        val retrofit = provider.createRetrofit("https://api.nytimes.com/")
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(ArticleSearchClientImpl(retrofit)) as T
    }
}
