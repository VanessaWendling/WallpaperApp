package com.vamg.wallpaperapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vamg.core.data.repository.PopularRemoteDataSrc
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.framework.network.response.DataWrapperResponse
import com.vamg.wallpaperapp.framework.network.response.toPhotoDomain
import javax.inject.Inject

class PopularPagingSrc @Inject constructor(
    private val dataSrc: PopularRemoteDataSrc<DataWrapperResponse>,
    private val pages: Int
) : PagingSource<Int, PhotoDomain>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoDomain>): Int? {
        // ?.let{} - verifica se é diferente de nulo
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDomain> {
        return try {
            val nextPage = params.key ?: PAGE_INDEX
            val popularResponse = dataSrc.fetchPopular(nextPage, pages)

            LoadResult.Page(
                data = popularResponse.photos.map { it.toPhotoDomain() },
                prevKey = null,
                nextKey = if (popularResponse.page >= nextPage) nextPage + PAGE_INDEX else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val PAGE_INDEX = 1
    }
}
