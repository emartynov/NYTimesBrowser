package nl.bijdorpstudio.common.article

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class NonEmptyString private constructor(val value: String) {

    companion object {
        fun of(value: String): NonEmptyString? =
            if (value.isEmpty()) null else NonEmptyString(value)
    }
}
