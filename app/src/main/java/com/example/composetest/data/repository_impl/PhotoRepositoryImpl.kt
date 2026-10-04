package com.example.composetest.data.repository_impl

import com.example.composetest.data.data_source.PhotosRemoteDataSource
import com.example.composetest.data.remapper.toEntity
import com.example.composetest.domain.entity.PhotoEntity
import com.example.composetest.domain.repository.PhotoRepository
import org.koin.core.annotation.Single

@Single
class PhotoRepositoryImpl(
    private val photosRemoteDataSource: PhotosRemoteDataSource
) : PhotoRepository {


    override suspend fun getPhotos(): List<PhotoEntity> {
        return photosRemoteDataSource.getPhotos().map { response ->
            response.toEntity()
        }
    }
}
