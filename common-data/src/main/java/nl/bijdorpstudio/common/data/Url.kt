package nl.bijdorpstudio.common.data

import java.net.URI

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class Url private constructor(val value: NonEmptyString) {

    companion object {
        fun of(value: String): Url? {
            return try {
                val nonEmptyString = NonEmptyString.of(value)

                if (nonEmptyString == null) {
                    null
                } else {
                    // check parsing
                    URI.create("http://google.com/$value")
                    Url(nonEmptyString)
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}
