package nl.bijdorpstudio.common.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NonEmptyStringShould {
    @Test
    fun `Return null from empty string`() {
        assertThat(NonEmptyString.of("")).isNull()
    }

    @Test
    fun `Return value for non empty string`() {
        assertThat(NonEmptyString.of("test")!!.value).isEqualTo("test")
    }
}
