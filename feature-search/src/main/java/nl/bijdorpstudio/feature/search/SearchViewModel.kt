package nl.bijdorpstudio.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import nl.bijdorpstudio.common.search.ArticleSearchClient
import nl.bijdorpstudio.feature.search.flex.FlexAdapter

// TODO: Tests
class SearchViewModel(private val searchClient: ArticleSearchClient) : RxViewModel() {
    private val mutableContentData = MutableLiveData<Content>()
    val contentDate: LiveData<Content> = mutableContentData

    fun refresh() {
        mutableContentData.value = Content.Loading

        searchClient.searchArticle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    val items = list.map { ArticleListFlexItem(it) }
                    mutableContentData.value = Content.Result(items)
                },
                onError = {
                    mutableContentData.value = Content.Error
                }
            ).addTo(compositeDisposable)
    }
}

sealed class Content {
    object Loading : Content()
    object Error : Content()
    data class Result(val items: List<FlexAdapter.Item>) : Content()
}
