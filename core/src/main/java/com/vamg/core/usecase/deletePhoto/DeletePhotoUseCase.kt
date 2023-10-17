package com.vamg.core.usecase.deletePhoto

import com.vamg.core.data.repository.dbrepository.GalleryRepository
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.core.usecase.base.CoroutinesDispatchers
import com.vamg.core.usecase.base.ResultStatus
import com.vamg.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeletePhotoUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val photoDomain: PhotoDomain)
}

class DeletePhotoUseCaseImpl @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<DeletePhotoUseCase.Params, Unit>(), DeletePhotoUseCase {
    override suspend fun doWork(params: DeletePhotoUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()){
            galleryRepository.delete(params.photoDomain)
            ResultStatus.Success(Unit)
        }
    }
}