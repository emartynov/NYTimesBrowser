package nl.bijdorpstudio.lib.retrofit

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class ApiKey private constructor(val key: String) {
    companion object {
        fun of(key: String) = if (key.isNotEmpty()) ApiKey(key) else null
    }
}
