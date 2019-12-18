package nl.bijdorpstudio.common.search

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
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
        whenever(retrofitMock.create(ArticleSearch::class.java)) doReturn searchMock

        val article = client.searchArticle().blockingGet()

        assertThat(article).isNotNull()
    }
}
