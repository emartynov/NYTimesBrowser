package nl.bijdorpstudio.common.article

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IDShould {
    @Test
    fun `Return null from empty string`() {
        assertThat(ID.of("")).isNull()
    }

    @Test
    fun `Return value for non empty string`() {
        assertThat(ID.of("test")!!.value.value).isEqualTo("test")
    }

}
