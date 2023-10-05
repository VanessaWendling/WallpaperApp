package com.vamg.core.data.repository.dbrepository

import com.vamg.core.domain.model.PhotoDomain
import kotlinx.coroutines.flow.Flow

interface GalleryLocalDataSource {
    suspend fun selectWallpaper() : Flow<List<PhotoDomain>>
    suspend fun insert(photoDomain: PhotoDomain)
    suspend fun delete(photoDomain: PhotoDomain)
}