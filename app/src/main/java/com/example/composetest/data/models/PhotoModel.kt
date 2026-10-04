package com.example.compose_clean_architecture.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PhotoModel(
    @SerialName("albumId")
    val albumID: Long? = null,

    val id: Long? = null,
    val title: String? = null,
    val url: String? = null,

    @SerialName("thumbnailUrl")
    val thumbnailURL: String? = null
)
