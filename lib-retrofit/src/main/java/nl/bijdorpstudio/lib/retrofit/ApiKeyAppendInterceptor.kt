package nl.bijdorpstudio.lib.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyAppendInterceptor(private val apiKey: ApiKey) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
            .addQueryParameter("api-key", apiKey.key)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
