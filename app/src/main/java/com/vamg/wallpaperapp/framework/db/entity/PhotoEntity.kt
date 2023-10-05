package com.vamg.wallpaperapp.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vamg.core.data.DbConstants
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.core.domain.model.SrcDomain

@Entity(tableName = DbConstants.PHOTO_TABLE_NAME)
data class PhotoEntity(
    @PrimaryKey
    val id: Int,
    val urlPhoto: String,
    val smallPhoto: String,
    val photographer: String,
    val avgColor: String
)

fun List<PhotoEntity>.toPhotoDomain() = map {
    PhotoDomain(
        id = it.id,
        photographer = it.photographer,
        avgColor = it.avgColor,
        srcDomain = SrcDomain(original =  it.urlPhoto, small = it.smallPhoto)
    )
}