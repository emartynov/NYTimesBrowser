package nl.bijdorpstudio.feature.search

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.common.article.ID
import nl.bijdorpstudio.common.search.ArticleSearchClient

class ArticleRepository(
    private val searchClient: ArticleSearchClient,
    private val favouriteStorage: FavouriteStorage
) {
    fun loadArticles(query: String, page: Int): Single<List<Article>> {
        return Single.zip(
            searchClient.searchArticle(query, page),
            favouriteStorage.favourites(),
            BiFunction<List<Article>, List<ID>, List<Article>> { articles, favourites ->
                articles.map { if (favourites.contains(it.id)) it.copy(isFavorite = true) else it }
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
