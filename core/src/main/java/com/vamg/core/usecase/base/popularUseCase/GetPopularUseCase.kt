package com.vamg.core.usecase.base.popularUseCase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vamg.core.data.repository.PopularRepository
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPopularUseCase {
    operator fun invoke(params: GetPopularParams): Flow<PagingData<PhotoDomain>>

    data class GetPopularParams(val pagingConfig: PagingConfig)
}

class GetPopularUseCaseImpl @Inject constructor(
    private val repository: PopularRepository
) : PagingUseCase<GetPopularUseCase.GetPopularParams, PhotoDomain>(), GetPopularUseCase {
    override fun createFlowObservable(params: GetPopularUseCase.GetPopularParams): Flow<PagingData<PhotoDomain>> {
        val pagingSource = repository.fetchPopular(pages = params.pagingConfig.pageSize)
        return Pager(config = params.pagingConfig) {
            pagingSource
        }.flow
    }
}