package nl.bijdorpstudio.common.article

@Suppress("DataClassPrivateConstructor")
data class NonEmptyString private constructor(val value: String) {

    companion object {
        fun of(value: String): NonEmptyString? =
            if (value.isEmpty()) null else NonEmptyString(value)
    }
}
