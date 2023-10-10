package com.vamg.wallpaperapp.ui.fragment.adapter.photoadapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.ui.fragment.adapter.viewholder.PhotoViewHolder

class PhotoAdapter(
    private val clickCallback: ((photo: PhotoDomain) -> Unit),
    private val longClickCallback: ((photo: PhotoDomain) -> Unit)
) : PagingDataAdapter<PhotoDomain, PhotoViewHolder>(differCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.create(parent, clickCallback, longClickCallback)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<PhotoDomain>() {
            override fun areItemsTheSame(oldItem: PhotoDomain, newItem: PhotoDomain): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: PhotoDomain, newItem: PhotoDomain): Boolean =
                oldItem == newItem

        }
    }
}