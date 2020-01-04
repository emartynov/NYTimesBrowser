package nl.bijdorpstudio.common.article

import nl.bijdorpstudio.common.data.NonEmptyString

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class ID private constructor(val value: NonEmptyString) {

    companion object {
        fun of(value: String): ID? {
            val nonEmptyString = NonEmptyString.of(value)

            return if (nonEmptyString == null) null else ID(nonEmptyString)
        }
    }
}
