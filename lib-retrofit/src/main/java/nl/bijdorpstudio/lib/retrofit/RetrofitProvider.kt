package nl.bijdorpstudio.lib.retrofit

import nl.bijdorpstudio.common.data.Url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitProvider(
    private val apiKeyAppendInterceptor: ApiKeyAppendInterceptor
) {
    fun createRetrofit(baseUrl: Url, isDebug: Boolean): Retrofit {
        var clientBuilder = OkHttpClient.Builder()
            .addInterceptor(apiKeyAppendInterceptor)

        if (isDebug) {
            clientBuilder = clientBuilder.addInterceptor(HttpLoggingInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl.value.value)
            .client(clientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}
