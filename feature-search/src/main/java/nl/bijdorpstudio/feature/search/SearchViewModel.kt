package nl.bijdorpstudio.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import nl.bijdorpstudio.common.navigation.Navigator

class SearchViewModel(
    private val articleRepository: ArticleRepository,
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
        articleRepository.loadArticles(query, page)
            .subscribeBy(
                onSuccess = { list ->
                    this.query = query
                    val items = list.map { article ->
                        ArticleListFlexItem(
                            article,
                            navigator::showArticleDetail,
                            articleRepository::favourite
                        )
                    }
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
