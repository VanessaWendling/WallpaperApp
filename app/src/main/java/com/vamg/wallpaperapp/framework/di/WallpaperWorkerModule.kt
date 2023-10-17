package com.vamg.wallpaperapp.framework.di

import android.app.WallpaperManager
import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WallpaperWorkerModule {

    @Provides
    fun providesWallpaperManager(@ApplicationContext context: Context): WallpaperManager =
        WallpaperManager.getInstance(context)

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}