package com.example.composetest.ui.screens.home.viewModel

import com.example.composetest.domain.entity.PhotoEntity

data class HomeState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val photos: List<PhotoEntity> = emptyList(),
    val errorMessage: String? = null,
)