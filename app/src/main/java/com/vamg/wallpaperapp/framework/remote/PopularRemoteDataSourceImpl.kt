package com.vamg.wallpaperapp.framework.remote

import com.vamg.core.data.repository.PopularRemoteDataSrc
import com.vamg.wallpaperapp.framework.network.api.WallpaperApi
import com.vamg.wallpaperapp.framework.network.response.DataWrapperResponse
import javax.inject.Inject

class PopularRemoteDataSourceImpl @Inject constructor(private val api: WallpaperApi) :
    PopularRemoteDataSrc<DataWrapperResponse> {
    override suspend fun fetchPopular(page: Int, perPage: Int): DataWrapperResponse =
        api.getPopularWallpapers(page, perPage)
}