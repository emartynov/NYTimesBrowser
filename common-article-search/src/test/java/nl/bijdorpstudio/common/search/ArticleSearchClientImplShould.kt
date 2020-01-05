package nl.bijdorpstudio.common.search

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import retrofit2.Retrofit

@Suppress("UsePropertyAccessSyntax")
@ExtendWith(MockitoExtension::class)
class ArticleSearchClientImplShould {
    @Mock
    private lateinit var retrofitMock: Retrofit
    @Mock
    private lateinit var searchMock: ArticleSearch

    @InjectMocks
    private lateinit var client: ArticleSearchClientImpl

    @Test
    fun `Correctly convert dto to data`() {
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
            ),
            url = "http://google.com",
            mainParagraph = "Main paragraph"
        )
        whenever(retrofitMock.create(ArticleSearch::class.java)) doReturn searchMock
        val articleSearchResultDTO = ArticleSearchResultDTO(ArticleSearchResponseDTO(listOf(dto)))
        whenever(searchMock.searchArticles("", 0)) doReturn Single.just(articleSearchResultDTO)

        val articles = client.searchArticle("", 0).blockingGet()

        assertThat(articles).hasSize(1)
        assertThat(articles[0]).isEqualTo(dto.toDomain())
    }
}
