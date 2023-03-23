package com.riyandifirman.githubuser

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService{
            // interceptor untuk menambahkan header pada setiap request
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "ghp_pijm3lkf059oxgy5xijfhypiwnt7yy3pjnvl")
                    .build()
                chain.proceed(requestHeaders)
            }

            // membuat client baru dengan interceptor yang sudah dibuat
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()

            // membuat retrofit baru dengan client yang sudah dibuat dan base url github api
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}