package com.vamg.wallpaperapp.ui.fragment.popular.viewmodel

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.core.usecase.base.popularUseCase.GetPopularUseCase
import com.vamg.core.usecase.insertphoto.InsertPhotoUseCase
import com.vamg.wallpaperapp.extentions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val popularUseCase: GetPopularUseCase,
    private val insertUseCase: InsertPhotoUseCase
) : ViewModel() {

    //MutableLiveData - pode mudar
    private val _favoriteUiState = MutableLiveData<FavoriteUiState>()

    //LiveData - não pode mudar
    val favoriteUiState: LiveData<FavoriteUiState> get() = _favoriteUiState

    fun popularWallpapers(): Flow<PagingData<PhotoDomain>> {
        return popularUseCase(
            GetPopularUseCase.GetPopularParams(getPagingConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPagingConfig() = PagingConfig(pageSize = 40)

    fun favoritePhoto(photoDomain: PhotoDomain) =
        viewModelScope.launch {
            photoDomain.run {
                insertUseCase(InsertPhotoUseCase.Params(this)).watchStatus(
                    loading = { _favoriteUiState.value = FavoriteUiState.Loading },
                    success = { _favoriteUiState.value = FavoriteUiState.FavoritePhoto(true) },
                    error = { _favoriteUiState.value = FavoriteUiState.FavoritePhoto(false) }
                )
            }
        }

    sealed class FavoriteUiState {
        object Loading : FavoriteUiState()
        class FavoritePhoto(val saved: Boolean) : FavoriteUiState()
        class FavoriteIcon(@DrawableRes val icon: Int) : FavoriteUiState()
    }
}