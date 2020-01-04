package nl.bijdorpstudio.lib.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL

class RetrofitProvider(
    private val apiKeyAppendInterceptor: ApiKeyAppendInterceptor,
    private val isDebug: Boolean
) {
    fun createRetrofit(baseUrl: URL): Retrofit {
        var clientBuilder = OkHttpClient.Builder()
            .addInterceptor(apiKeyAppendInterceptor)

        if (isDebug) {
            clientBuilder = clientBuilder.addInterceptor(HttpLoggingInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}
