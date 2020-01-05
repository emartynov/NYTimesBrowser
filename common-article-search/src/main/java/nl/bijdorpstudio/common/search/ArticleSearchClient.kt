package nl.bijdorpstudio.common.search

import io.reactivex.Single
import nl.bijdorpstudio.common.article.Article
import retrofit2.Retrofit

interface ArticleSearchClient {
    fun searchArticle(query: String = "", page: Int = 0): Single<List<Article>>
}

class ArticleSearchClientImpl(private val retrofit: Retrofit) : ArticleSearchClient {
    override fun searchArticle(query: String, page: Int): Single<List<Article>> {
        return retrofit.create(ArticleSearch::class.java)
            .searchArticles(query, page)
            .map {
                it.response
                    .documents
                    .map { articleDTO -> articleDTO.toDomain() }
            }
    }
}
