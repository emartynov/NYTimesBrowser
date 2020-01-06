package nl.bijdorpstudio.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import nl.bijdorpstudio.common.navigation.Navigator
import nl.bijdorpstudio.common.search.ArticleSearchClient

class SearchViewModel(
    private val searchClient: ArticleSearchClient,
    private val navigator: Navigator
) : RxViewModel() {
    private val mutableContentData = MutableLiveData<Content>()
    val contentData: LiveData<Content> = mutableContentData

    private val loadedItems = mutableListOf<ArticleListFlexItem>()

    private var page = 0
    private var query = ""

    fun refresh(query: String = "") {
        page = 0
        loadedItems.clear()
        compositeDisposable.clear()

        mutableContentData.value = Content.Loading

        loadPageItems(query)
    }

    private fun loadPageItems(query: String) {
        searchClient.searchArticle(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    this.query = query
                    val items = list.map { ArticleListFlexItem(it, navigator::showArticleDetail) }
                    loadedItems += items
                    mutableContentData.value = Content.Result(loadedItems.toMutableList())
                },
                onError = {
                    mutableContentData.value = Content.Error
                }
            ).addTo(compositeDisposable)
    }

    fun loadNext() {
        page += 1

        loadPageItems(query)
    }

    fun search(query: String) {
        if (this.query != query) {
            refresh(query)
        }
    }
}

sealed class Content {
    object Loading : Content()
    object Error : Content()
    data class Result(val items: List<nl.bijdorpstudio.lib.flex.FlexAdapter.Item>) : Content()
}
