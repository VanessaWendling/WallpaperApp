package com.vamg.wallpaperapp.framework.network.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class ApiClient<T>(
    url: String,
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
) {
    //    factory - recebe e converte em algo
    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()


    fun createApi(apiInterface: Class<T>): T = retrofit.create(apiInterface)


}