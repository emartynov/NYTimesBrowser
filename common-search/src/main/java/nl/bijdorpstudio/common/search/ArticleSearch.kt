package nl.bijdorpstudio.common.search

import io.reactivex.Single
import nl.bijdorpstudio.common.article.NonEmptyString
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ArticleSearch {
    @GET("svc/search/v2/articlesearch.json")
    fun searchArticles(@Query("apikey") apiKey: NonEmptyString): Single<ArticleSearchResult>
}
