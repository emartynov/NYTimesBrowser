package nl.bijdorpstudio.common.search

import io.reactivex.Single
import nl.bijdorpstudio.common.article.Article
import retrofit2.Retrofit

interface ArticleSearchClient {
    fun searchArticle(): Single<List<Article>>
}

class ArticleSearchClientImpl(private val retrofit: Retrofit) : ArticleSearchClient {
    override fun searchArticle(): Single<List<Article>> {
        return retrofit.create(ArticleSearch::class.java)
            .searchArticles()
            .map {
                it.response
                    .documents
                    .map { articleDTO -> articleDTO.toDomain() }
            }
    }
}
