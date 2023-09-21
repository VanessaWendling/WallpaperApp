package com.vamg.wallpaperapp.ui.fragment.popular.viewmodel

import com.vamg.core.usecase.popularUseCase.GetPopularUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class PopularViewModelTest {

    @Mock
    lateinit var popularUseCase: GetPopularUseCase
    lateinit var popularViewModel: PopularViewModel

    @Before
    fun setup() {
        popularViewModel = PopularViewModel(popularUseCase)
    }

    @Test
    fun `Should validate pagination data`() = runTest {

    }

}