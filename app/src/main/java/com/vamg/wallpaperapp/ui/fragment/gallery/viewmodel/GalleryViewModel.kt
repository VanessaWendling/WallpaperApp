package com.vamg.wallpaperapp.ui.fragment.gallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.core.usecase.base.CoroutinesDispatchers
import com.vamg.core.usecase.deletePhoto.DeletePhotoUseCase
import com.vamg.core.usecase.getallphotos.GetAllPhotosUseCase
import com.vamg.wallpaperapp.extentions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getAllPhotosUseCase: GetAllPhotosUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase,
    private val dispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()


    init {
        getGallery()
    }

    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(dispatchers.main()) {
            when (action) {
                is Action.GetGallery -> {
                    get()
                }

                is Action.DeleteFavorite -> {
                    delete(action)
                }
            }
        }
    }

    fun deleteFavorite(photoDomain: PhotoDomain) {
        action.value = Action.DeleteFavorite(photoDomain)
    }

    private suspend fun LiveDataScope<UiState>.get() {
        getAllPhotosUseCase.invoke().catch {
            emit(UiState.Error)
        }.collect {
            val uiState = if (it.isEmpty())
                UiState.EmptyGallery
            else
                UiState.ShowGallery(it)

            emit(uiState)
        }
    }

    private suspend fun LiveDataScope<UiState>.delete(action: Action.DeleteFavorite) {
        deletePhotoUseCase(DeletePhotoUseCase.Params(action.photoDomain)).watchStatus(
            loading = {},
            success = { getGallery() },
            error = { emit(UiState.Error) }
        )
    }

    private fun getGallery() {
        action.value = Action.GetGallery
    }

    sealed class Action {
        object GetGallery : Action()
        data class DeleteFavorite(val photoDomain: PhotoDomain) : Action()
    }

    sealed class UiState {
        data class ShowGallery(val favorites: List<PhotoDomain>) : UiState()
        object EmptyGallery : UiState()
        object Error : UiState()
    }
}