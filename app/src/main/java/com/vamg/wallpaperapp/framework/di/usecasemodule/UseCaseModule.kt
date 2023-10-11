package com.vamg.wallpaperapp.framework.di.usecasemodule

import com.vamg.core.usecase.base.popularUseCase.GetPopularUseCase
import com.vamg.core.usecase.base.popularUseCase.GetPopularUseCaseImpl
import com.vamg.core.usecase.getallphotos.GetAllPhotosUseCase
import com.vamg.core.usecase.getallphotos.GetAllPhotosUseCaseImpl
import com.vamg.core.usecase.insertphoto.InsertPhotoUseCase
import com.vamg.core.usecase.insertphoto.InsertPhotoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindPopularUseCase(useCase: GetPopularUseCaseImpl): GetPopularUseCase

    @Binds
    fun bindInsertPhotoUseCase(useCase: InsertPhotoUseCaseImpl): InsertPhotoUseCase

    @Binds
    fun bindGetAllPhotosUseCase(useCase: GetAllPhotosUseCaseImpl): GetAllPhotosUseCase
}