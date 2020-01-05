package nl.bijdorpstudio.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import nl.bijdorpstudio.common.navigation.Navigator
import nl.bijdorpstudio.common.search.ArticleSearchClient

// TODO: Tests
class SearchViewModel(
    private val searchClient: ArticleSearchClient,
    private val navigator: Navigator
) : RxViewModel() {
    private val mutableContentData = MutableLiveData<Content>()
    val contentData: LiveData<Content> = mutableContentData

    private val loadedItems = mutableListOf<ArticleListFlexItem>()

    private var page = 0
    private var query = ""

    fun refresh() {
        page = 0
        loadedItems.clear()
        compositeDisposable.clear()

        mutableContentData.value = Content.Loading

        loadPageItems()
    }

    private fun loadPageItems() {
        searchClient.searchArticle(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
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

        loadPageItems()
    }

    fun search(query: String) {
        if (this.query != query) {
            this.query = query
            refresh()
        }
    }
}

sealed class Content {
    object Loading : Content()
    object Error : Content()
    data class Result(val items: List<nl.bijdorpstudio.lib.flex.FlexAdapter.Item>) : Content()
}
