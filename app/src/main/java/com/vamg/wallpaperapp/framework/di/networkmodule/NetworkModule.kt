package com.vamg.wallpaperapp.framework.di.networkmodule

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vamg.wallpaperapp.BuildConfig
import com.vamg.wallpaperapp.framework.network.api.WallpaperApi
import com.vamg.wallpaperapp.framework.network.interceptor.AuthorizationInterceptor
import com.vamg.wallpaperapp.framework.network.retrofit.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {
    private const val TIMEOUT_SECONDS = 15L

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    fun provideOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideWallpapersApi(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): WallpaperApi =
        ApiClient<WallpaperApi>(BuildConfig.BASE_URL, okHttpClient, converterFactory).createApi(
            WallpaperApi::class.java
        )
}