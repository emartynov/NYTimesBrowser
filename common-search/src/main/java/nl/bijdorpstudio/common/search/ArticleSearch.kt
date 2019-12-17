package nl.bijdorpstudio.common.search

import io.reactivex.Single
import retrofit2.http.GET

internal interface ArticleSearch {
    @GET("svc/search/v2/articlesearch.json")
    fun searchArticles(): Single<ArticleSearchResultDTO>
}
