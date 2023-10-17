package com.vamg.wallpaperapp.ui.fragment.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.databinding.ItemPhotoBinding
import com.vamg.wallpaperapp.extentions.loadBlurredImageWithPlaceholder

class PhotoViewHolder(
    itemBinding: ItemPhotoBinding,
    private val clickCallback: (photo: PhotoDomain) -> Unit,
    private val longClickCallback: (photo: PhotoDomain) -> Unit,

    ) : RecyclerView.ViewHolder(itemBinding.root) {

    private val image = itemBinding.image
    private val name = itemBinding.name

    fun bind(photo: PhotoDomain) {
        image.loadBlurredImageWithPlaceholder(
            imageUrl = photo.srcDomain?.original,
            placeholderColor = photo.avgColor
        )

        name.text = photo.photographer
        itemView.setOnClickListener {
            clickCallback.invoke(photo)
        }
        itemView.setOnLongClickListener {
            longClickCallback.invoke(photo)
            return@setOnLongClickListener true
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            photoCallback: (photo: PhotoDomain) -> Unit,
            longClickCallback: ((photo: PhotoDomain) -> Unit)
        ): PhotoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemPhotoBinding.inflate(inflater, parent, false)
            return PhotoViewHolder(itemBinding, photoCallback, longClickCallback)
        }
    }

}