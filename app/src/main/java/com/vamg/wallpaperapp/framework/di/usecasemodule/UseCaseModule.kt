package com.vamg.wallpaperapp.framework.di.usecasemodule

import com.vamg.core.usecase.base.popularUseCase.GetPopularUseCase
import com.vamg.core.usecase.base.popularUseCase.GetPopularUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindPopularUseCase(useCase: GetPopularUseCaseImpl): GetPopularUseCase
}