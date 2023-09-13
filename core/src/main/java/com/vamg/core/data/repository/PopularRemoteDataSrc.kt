package com.vamg.core.data.repository

interface PopularRemoteDataSrc<T> {
    suspend fun fetchPopular(page:Int, perPage:Int):T
}