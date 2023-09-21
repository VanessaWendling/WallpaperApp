package com.vamg.wallpaperapp.ui.fragment.popular.viewmodel

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.vamg.core.usecase.popularUseCase.GetPopularUseCase
import com.vamg.testing.coroutinerule.MainCoroutineRule
import com.vamg.testing.model.WallpapersFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class PopularViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var popularUseCase: GetPopularUseCase
    private lateinit var popularViewModel: PopularViewModel

    @Before
    fun setup() {
        popularViewModel = PopularViewModel(popularUseCase)
    }

    @Test
    fun `Should validate pagination data`() = runTest {

        whenever(popularUseCase(any())).thenReturn(flowOf(getPagingDataMock))

        val result = popularViewModel.popularWallpapers()

        assertNotNull(result.first())
    }

    private val wallpapersFactory = WallpapersFactory()

    private val getPagingDataMock =
        PagingData.from(
            listOf(
                wallpapersFactory.create(WallpapersFactory.Photo.PhotoDomainSuccess),
                wallpapersFactory.create(WallpapersFactory.Photo.PhotoDomainSuccess)
            )
        )
}