package nl.bijdorpstudio.common.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UrlShould {
    @Test
    fun `Return null from empty string`() {
        assertThat(Url.of("")).isNull()
    }

    @Test
    fun `Return value for non empty string`() {
        assertThat(Url.of("path/to/some.png")!!.value.value).isEqualTo("path/to/some.png")
    }

    @Test
    fun `Return null if can not parse url`() {
        assertThat(Url.of("path\\d")).isNull()
    }
}
