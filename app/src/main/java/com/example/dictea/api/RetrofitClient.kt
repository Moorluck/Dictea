package com.example.dictea.api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request();
        return chain.proceed(request)
    }
}


object RetrofitClient {
    private const val TAG = "RetrofitClient"

    private const val BASE_URL = "https://linguee-api-v2.herokuapp.com/api/v2/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient().newBuilder()
            .addInterceptor(RequestInterceptor)
            .build()
    }


    val client : Retrofit by lazy {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        client
    }
}