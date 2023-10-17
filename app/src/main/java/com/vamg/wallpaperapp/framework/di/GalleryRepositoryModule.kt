package com.vamg.wallpaperapp.framework.di

import com.vamg.core.data.repository.dbrepository.GalleryLocalDataSource
import com.vamg.core.data.repository.dbrepository.GalleryRepository
import com.vamg.wallpaperapp.framework.db.repository.GalleryRepositoryImpl
import com.vamg.wallpaperapp.framework.local.RoomGalleryDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GalleryRepositoryModule {
    @Binds
    fun bindGalleryRepository(repository: GalleryRepositoryImpl): GalleryRepository

    @Binds
    fun bindGalleryLocalDataSource(dataSource: RoomGalleryDataSource): GalleryLocalDataSource
}