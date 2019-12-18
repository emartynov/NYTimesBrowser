package nl.bijdorpstudio.common.search

import com.squareup.moshi.Moshi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

private val JSON = """
    {
        "response" : {
            "docs" : [ 
                {
                    "_id": "nyt://recipe/4bfb1898-5152-5f6b-991b-6d30cf764b96",
                    "pub_date": "2019-12-18T00:00:00+0000",
                    "headline" : {
                        "name" : "Golden Ginger Cake"
                    },
                    "multimedia" : [
                        {
                            "subtype": "slide",
                            "type": "image",
                            "url": "images/2019/12/18/dining/17Romanrex3/merlin_162383772_d1e931a2-acf1-402a-ba48-77c3b8b0004a-slide.jpg",
                            "height": 400,
                            "width": 600
                        }
                    ]
                }
            ]
        }
    }
""".trimIndent()


class ArticleSearchResultShould {
    @Test
    fun `Parse json correctly`() {
        val adapter = Moshi.Builder().build().adapter(ArticleSearchResultDTO::class.java)

        val result = adapter.fromJson(JSON) as ArticleSearchResultDTO

        assertThat(result.response.documents).hasSize(1)
        val articleDTO = result.response.documents[0]
        assertThat(articleDTO.id).isEqualTo("nyt://recipe/4bfb1898-5152-5f6b-991b-6d30cf764b96")
        assertThat(articleDTO.date).isEqualTo("2019-12-18T00:00:00+0000")
        assertThat(articleDTO.headline.name).isEqualTo("Golden Ginger Cake")
        assertThat(articleDTO.multimedia).hasSize(1)
        val multimedia = articleDTO.multimedia[0]
        assertThat(multimedia.type).isEqualTo("image")
        assertThat(multimedia.subtype).isEqualTo("slide")
        assertThat(multimedia.url)
            .isEqualTo("images/2019/12/18/dining/17Romanrex3/merlin_162383772_d1e931a2-acf1-402a-ba48-77c3b8b0004a-slide.jpg")
    }

    @Test
    fun `Correctly convert to domain`() {
        val dto = ArticleDTO(
            id = "some id",
            date = "2019-12-18T00:00:00+0000",
            headline = HeadlineDTO("Author"),
            multimedia = listOf(
                MultimediaDTO(
                    type = "image",
                    subtype = "slide",
                    url = "images/2019/12/18/slide.jpg"
                )
            )
        )

        val article = dto.toDomain()

        assertThat(article.title.value).isEqualTo(dto.headline.name)
        assertThat(article.date).isEqualTo(LocalDateTime.of(2019, 12, 18, 0, 0, 0))
        assertThat(article.id.value.value).isEqualTo("some id")
        assertThat(article.imageUrl.value.value).isEqualTo("images/2019/12/18/slide.jpg")
    }
}
