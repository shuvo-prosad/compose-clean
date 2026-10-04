package com.example.composetest.data.data_source

import com.example.compose_clean_architecture.data.models.PhotoModel
import retrofit2.http.GET

interface PhotosRemoteDataSource {
    @GET("/photos")
    suspend fun getPhotos(): List<PhotoModel>
}
