package com.vamg.core.data.repository

import androidx.paging.PagingSource
import com.vamg.core.domain.model.PhotoDomain

interface PopularRepository {
    fun fetchPopular(pages: Int): PagingSource<Int,PhotoDomain>
}