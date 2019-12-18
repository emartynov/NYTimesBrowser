package nl.bijdorpstudio.common.article

class ID private constructor(val value: NonEmptyString) {

    companion object {
        fun of(value: String): ID? {
            val nonEmptyString = NonEmptyString.of(value)

            return if (nonEmptyString == null) null else ID(nonEmptyString)
        }
    }
}
