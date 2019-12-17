package nl.bijdorpstudio.common.search

import com.squareup.moshi.Moshi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private val JSON = """
    {
        "response" : {
            "docs" : [ 
                {
                    "_id": "nyt://recipe/4bfb1898-5152-5f6b-991b-6d30cf764b96"
                }
            ]
        }
    }
""".trimIndent()


class ArticleSearchResultShould {
    @Test
    fun `Parse json correctly`() {
        val adapter = Moshi.Builder().build().adapter(ArticleSearchResult::class.java)

        val result = adapter.fromJson(JSON) as ArticleSearchResult

        assertThat(result.response.documents).hasSize(1)
        assertThat(result.response.documents[0].id)
            .isEqualTo("nyt://recipe/4bfb1898-5152-5f6b-991b-6d30cf764b96")
    }
}
