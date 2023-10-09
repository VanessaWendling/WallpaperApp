package com.vamg.wallpaperapp.framework.di

import com.vamg.core.usecase.base.AppCoroutinesDispatcher
import com.vamg.core.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DispatcherModule {
    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatcher): CoroutinesDispatchers
}