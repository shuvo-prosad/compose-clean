package com.example.composetest.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.domain.use_cases.GetPhotosListUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getPhotosListUseCase: GetPhotosListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState(isLoading = true))
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        loadPhotos()
    }

    fun loadPhotos() = fetchPhotos(isRefresh = false)

    fun refresh() = fetchPhotos(isRefresh = true)

    private fun fetchPhotos(isRefresh: Boolean) {
        if (_uiState.value.isRefreshing) return // ignore repeated pulls

        viewModelScope.launch {
            _uiState.update {
                if (isRefresh) it.copy(isRefreshing = true)
                else it.copy(isLoading = true, errorMessage = null)
            }
            try {
                val photos = getPhotosListUseCase.execute()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        photos = photos,
                        errorMessage = null,
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        // keep showing existing photos if a refresh fails
                        errorMessage = if (it.photos.isEmpty()) {
                            e.message ?: "Something went wrong"
                        } else null,
                    )
                }
            }
        }
    }
}