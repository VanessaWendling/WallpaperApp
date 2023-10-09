package com.vamg.wallpaperapp.framework.di

import android.content.Context
import androidx.room.Room
import com.vamg.core.data.DbConstants
import com.vamg.wallpaperapp.framework.db.WallpaperAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    fun providerWallpaperDatabase(@ApplicationContext app: Context): WallpaperAppDatabase =
        Room.databaseBuilder(
            app,
            WallpaperAppDatabase::class.java,
            DbConstants.APP_DATA_BASE_NAME
        ).build()

    @Provides
    fun providerWallpaperDao(database: WallpaperAppDatabase) = database.wallpapperDao()

}