package com.example.lifehack.data.api

import com.example.lifehack.data.Utils.URL_API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiDataConnect {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder().addInterceptor(logger)

    val interceptors = Interceptor { chain ->
        val request = chain.request()

        val call = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .method(request.method, request.body)
            .build()

        chain.proceed(call)
    }

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    val api: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val API_POSTS : PostsApi by lazy {
        retrofit.create(PostsApi::class.java)
    }
}