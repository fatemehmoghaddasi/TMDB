package com.example.tmdb.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.model.BasicMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    private val _UiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val UiState: StateFlow<DetailUiState> = _UiState.asStateFlow()
    fun getMovieById(id: Long) {
        viewModelScope.launch {
            _UiState.value = DetailUiState.Loading
            movieRepository.getMovieById(id)
                .onSuccess {
                    _UiState.value = DetailUiState.Success(it)
                }
                .onFailure {
                    _UiState.value = DetailUiState.Error(it)
                }
        }
    }
}

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val movie: BasicMovie) : DetailUiState
    data class Error(val exception: Throwable) : DetailUiState
}