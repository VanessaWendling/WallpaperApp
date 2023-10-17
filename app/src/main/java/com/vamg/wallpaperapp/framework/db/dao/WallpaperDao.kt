package com.vamg.wallpaperapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vamg.core.data.DbConstants
import com.vamg.wallpaperapp.framework.db.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PhotoEntity)

    @Query("SELECT * FROM ${DbConstants.PHOTO_TABLE_NAME}")
     fun getAllPhotos(): Flow<List<PhotoEntity>>

     @Delete
     suspend fun delete(entity: PhotoEntity)

     @Query("SELECT * FROM ${DbConstants.PHOTO_TABLE_NAME} ORDER BY RANDOM() LIMIT 1")
     suspend fun selectRandomDownloadedWallpaper(): PhotoEntity
}