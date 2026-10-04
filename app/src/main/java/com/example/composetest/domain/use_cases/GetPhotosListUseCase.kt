package com.example.composetest.domain.use_cases

import com.example.composetest.domain.entity.PhotoEntity
import com.example.composetest.domain.repository.PhotoRepository
import org.koin.core.annotation.Single

@Single
class GetPhotosListUseCase(
    private val photoRepository: PhotoRepository
) {
    suspend fun execute(): List<PhotoEntity> = photoRepository.getPhotos()
}
