package com.vamg.core.usecase.insertphoto

import com.vamg.core.data.repository.dbrepository.GalleryRepository
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.core.usecase.base.CoroutinesDispatchers
import com.vamg.core.usecase.base.ResultStatus
import com.vamg.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface InsertPhotoUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>
    data class Params(val photo: PhotoDomain)
}

class InsertPhotoUseCaseImpl @Inject constructor(
    private val repository: GalleryRepository,
    private val dispatcher: CoroutinesDispatchers
) : UseCase<InsertPhotoUseCase.Params, Unit>(), InsertPhotoUseCase {

    override suspend fun doWork(params: InsertPhotoUseCase.Params): ResultStatus<Unit> =
        withContext(dispatcher.io()){
            repository.insert(params.photo)
            ResultStatus.Success(Unit)
        }
}
