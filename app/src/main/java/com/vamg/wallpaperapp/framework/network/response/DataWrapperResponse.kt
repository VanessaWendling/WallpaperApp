package com.vamg.wallpaperapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataWrapperResponse(

    // os getters, setters e construtores são criados sozinhos
    @SerializedName("next_page")
    val nextPage: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("photos")
    val photos: List<Photo>
)