package nl.bijdorpstudio.common.article

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UrlShould {
    @Test
    fun `Return null from empty string`() {
        assertThat(Url.of("")).isNull()
    }

    @Test
    fun `Return value for non empty string`() {
        assertThat(Url.of("test")!!.value.value).isEqualTo("test")
    }

}
