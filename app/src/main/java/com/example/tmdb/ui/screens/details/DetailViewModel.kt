package com.example.tmdb.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.Credits
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailViewModel.DetailViewModelFactory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted private val movieId: Long,
    private val movieRepository: MovieRepository
) : ViewModel() {


    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(movieId: Long): DetailViewModel
    }

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        getMovieById(movieId)
    }

    private fun getMovieById(movieId: Long) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            movieRepository.getMovieById(movieId)
                .onSuccess { movie ->
                    _uiState.value = DetailUiState.Success(
                        movie = movie,
                        credits = movie.credits!!
                    )
                }
                .onFailure {
                    _uiState.value = DetailUiState.Error(it)
                }
        }

    }
}

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(
        val movie: BasicMovie,
        val credits: Credits
    ) : DetailUiState
    data class Error(val exception: Throwable) : DetailUiState
}













