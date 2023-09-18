package com.vamg.wallpaperapp.framework.di.repositorymodule

import com.vamg.core.data.repository.PopularRemoteDataSrc
import com.vamg.core.data.repository.PopularRepository
import com.vamg.wallpaperapp.framework.network.response.DataWrapperResponse
import com.vamg.wallpaperapp.framework.remote.PopularRemoteDataSourceImpl
import com.vamg.wallpaperapp.framework.repository.PopularRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface WallpapersRepositoryModule {

    @Binds
    fun bindPopularRepository(repository: PopularRepositoryImpl):PopularRepository

    @Binds
    fun bindPopularRemoteDataSource(dataSrc: PopularRemoteDataSourceImpl
    ): PopularRemoteDataSrc<DataWrapperResponse>
}