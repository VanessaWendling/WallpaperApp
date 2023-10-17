package com.vamg.wallpaperapp.ui.fragment.popular.viewmodel

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.vamg.core.usecase.base.popularUseCase.GetPopularUseCase
import com.vamg.core.usecase.insertphoto.InsertPhotoUseCase
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

    @Mock //o que vem no construtor
    lateinit var popularUseCase: GetPopularUseCase

    @Mock //o que vem no construtor
    lateinit var insertPhotoUseCase: InsertPhotoUseCase

    //a classe que possui o método pra teste
    private lateinit var popularViewModel: PopularViewModel

    //saída
    private val wallpapersFactory = WallpapersFactory()
    private val getPagingDataMock =
        PagingData.from(
            listOf(
                wallpapersFactory.create(WallpapersFactory.Photo.PhotoDomainSuccess),
                wallpapersFactory.create(WallpapersFactory.Photo.PhotoDomainSuccess)
            )
        )

    @Before
    fun setup() {
        popularViewModel = PopularViewModel(popularUseCase, insertPhotoUseCase)
    }

    @Test
    fun `Should validate pagination data`() = runTest {
        whenever(popularUseCase(any())).thenReturn(flowOf(getPagingDataMock))

        val result = popularViewModel.popularWallpapers()

        assertNotNull(result.first())
    }

    @Test(expected = RuntimeException::class)
    fun `Should return an empty PagingData When an error occurred`() = runTest {
        whenever(popularUseCase(any())).thenThrow(RuntimeException())

        popularViewModel.popularWallpapers()
    }

}