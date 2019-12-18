package nl.bijdorpstudio.lib.retrofit

import okhttp3.Interceptor
import okhttp3.Response

// TODO class for ApiKey
class ApiKeyAppendInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
            .addQueryParameter("api-key", apiKey)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
