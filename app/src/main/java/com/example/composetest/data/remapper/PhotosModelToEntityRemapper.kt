package com.example.composetest.data.remapper

import com.example.compose_clean_architecture.data.models.PhotoModel
import com.example.composetest.domain.entity.PhotoEntity

fun PhotoModel.toEntity(): PhotoEntity {
    return PhotoEntity(
        albumId = this.albumID ?: 0,
        id = this.id ?: 0,
        title = this.title ?: "",
        url = this.url ?: "",
        thumbnailUrl = this.thumbnailURL ?: ""
    );
}
