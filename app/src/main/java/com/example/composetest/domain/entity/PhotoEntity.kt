package com.example.composetest.domain.entity


data class PhotoEntity(
    val albumId: Long,
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
