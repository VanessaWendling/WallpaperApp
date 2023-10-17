package com.vamg.core.usecase.popularUseCase

import androidx.paging.PagingConfig
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.vamg.core.data.repository.PopularRepository
import com.vamg.core.usecase.base.popularUseCase.GetPopularUseCase
import com.vamg.core.usecase.base.popularUseCase.GetPopularUseCaseImpl
import com.vamg.testing.coroutinerule.MainCoroutineRule
import com.vamg.testing.model.WallpapersFactory
import com.vamg.testing.pagingsource.PagingSourceFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class GetPopularUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: PopularRepository;

    private val photos = WallpapersFactory().create(WallpapersFactory.Photo.PhotoDomainSuccess)
    private val mockPagingSource = PagingSourceFactory().create(listOf(photos))

    private lateinit var getPopularUseCase: GetPopularUseCase

    @Before
    fun setup() {
        getPopularUseCase = GetPopularUseCaseImpl(repository)
    }


    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {
            whenever(repository.fetchPopular(40)).thenReturn(mockPagingSource)

            val result =
                getPopularUseCase.invoke(GetPopularUseCase.GetPopularParams(PagingConfig(40)))

            verify(repository).fetchPopular(40)

            assertNotNull(result.first())
        }

    @Test(expected = RuntimeException::class)
    fun `should return an error when flow paging data creation is called`() = runTest {
        whenever(repository.fetchPopular(40)).thenThrow(RuntimeException())

        getPopularUseCase(GetPopularUseCase.GetPopularParams(PagingConfig(40)))
    }
}