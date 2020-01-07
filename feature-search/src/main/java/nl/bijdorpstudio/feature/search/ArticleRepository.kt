package nl.bijdorpstudio.feature.search

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.common.search.ArticleSearchClient

class ArticleRepository(
    private val searchClient: ArticleSearchClient
) {
    fun loadArticles(query: String, page: Int): Single<List<Article>> {
        return searchClient.searchArticle(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
