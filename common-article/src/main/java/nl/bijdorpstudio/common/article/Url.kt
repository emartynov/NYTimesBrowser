package nl.bijdorpstudio.common.article

class Url private constructor(val value: NonEmptyString) {

    companion object {
        fun of(value: String): Url? {
            val nonEmptyString = NonEmptyString.of(value)

            return if (nonEmptyString == null) null else Url(nonEmptyString)
        }
    }
}
