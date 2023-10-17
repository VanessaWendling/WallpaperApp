package com.vamg.wallpaperapp.framework.repository

import androidx.paging.PagingSource
import com.vamg.core.data.repository.PopularRemoteDataSrc
import com.vamg.core.data.repository.PopularRepository
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.framework.network.response.DataWrapperResponse
import com.vamg.wallpaperapp.framework.paging.PopularPagingSrc
import javax.inject.Inject

class PopularRepositoryImpl @Inject constructor(
    private val remoteDataSrc: PopularRemoteDataSrc<DataWrapperResponse>
) :
    PopularRepository {
    override fun fetchPopular(pages: Int): PagingSource<Int, PhotoDomain> {
        return PopularPagingSrc(remoteDataSrc, pages)
    }
}