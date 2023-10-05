package com.vamg.wallpaperapp.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vamg.wallpaperapp.framework.db.dao.WallpaperDao
import com.vamg.wallpaperapp.framework.db.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class WallpaperAppDatabase : RoomDatabase() {
    abstract fun wallpapperDao(): WallpaperDao

}
