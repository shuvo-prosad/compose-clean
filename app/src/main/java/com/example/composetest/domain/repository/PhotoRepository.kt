package com.example.composetest.domain.repository

import com.example.composetest.domain.entity.PhotoEntity

interface PhotoRepository {
    suspend fun getPhotos(): List<PhotoEntity>
}
