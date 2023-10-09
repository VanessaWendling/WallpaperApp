package com.vamg.wallpaperapp.framework.db.repository

import com.vamg.core.data.repository.dbrepository.GalleryLocalDataSource
import com.vamg.core.data.repository.dbrepository.GalleryRepository
import com.vamg.core.domain.model.PhotoDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val dataSource: GalleryLocalDataSource
) : GalleryRepository {
    override suspend fun selectWallpaper(): Flow<List<PhotoDomain>> =
        dataSource.selectWallpaper()

    override suspend fun insert(photoDomain: PhotoDomain) =
        dataSource.insert(photoDomain)

    override suspend fun delete(photoDomain: PhotoDomain) =
        dataSource.delete(photoDomain)

}