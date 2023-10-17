package com.vamg.wallpaperapp.framework.local

import com.vamg.core.data.repository.dbrepository.GalleryLocalDataSource
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.framework.db.dao.WallpaperDao
import com.vamg.wallpaperapp.framework.db.entity.PhotoEntity
import com.vamg.wallpaperapp.framework.db.entity.toPhotoDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomGalleryDataSource @Inject constructor(
    private val dao: WallpaperDao
) : GalleryLocalDataSource {
    override suspend fun selectWallpaper(): Flow<List<PhotoDomain>> =
        dao.getAllPhotos().map { it.toPhotoDomain() }

    override suspend fun insert(photoDomain: PhotoDomain) =
        dao.insert(photoDomain.toEntity())

    override suspend fun delete(photoDomain: PhotoDomain) =
        dao.delete(photoDomain.toEntity())

    private fun PhotoDomain.toEntity() =
        PhotoEntity(
            id = this.id ?: 0,
            urlPhoto = this.srcDomain?.original ?: "",
            smallPhoto = this.srcDomain?.small ?: "",
            avgColor = this.avgColor ?: "",
            photographer = this.photographer ?: ""
        )

}