package com.vamg.wallpaperapp.framework.repository

import com.nhaarman.mockitokotlin2.whenever
import com.vamg.core.data.repository.PopularRemoteDataSrc
import com.vamg.core.data.repository.PopularRepository
import com.vamg.testing.coroutinerule.MainCoroutineRule
import com.vamg.wallpaperapp.factory.DataWrapperResponseFactory
import com.vamg.wallpaperapp.framework.network.response.DataWrapperResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class PopularRepositoryImplTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var remoteDataSrc: PopularRemoteDataSrc<DataWrapperResponse>
    private lateinit var popularRepository: PopularRepository
    private val dataWrapperResponseFactory = DataWrapperResponseFactory().create()
    @Before
    fun setup() {
        popularRepository = PopularRepositoryImpl(remoteDataSrc)
    }

    @Test
    fun `should validate function fetch popular`() = runTest {
        whenever(remoteDataSrc.fetchPopular(1, 40)).thenReturn(dataWrapperResponseFactory)

        val result = popularRepository.fetchPopular(40)

        assertNotNull(result)
    }

}