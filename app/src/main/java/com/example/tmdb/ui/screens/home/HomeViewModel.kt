package com.example.tmdb.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.MovieListType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    // private val movieService: MovieService
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _UiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _UiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _UiState.value = HomeUiState.Loading

        viewModelScope.launch {
            movieRepository.getBasicMovieList(MovieListType.NOW_PLAYING)
                .onSuccess { movie ->
                  //  _UiState.value = HomeUiState.Success(movies = it)
                    _UiState.update {
                        if (it is HomeUiState.Success) {
                            it.copy(nowPlayingMovies = movie)
                        } else {
                            HomeUiState.Success(nowPlayingMovies = movie)
                        }
                    }
                }

                .onFailure {
                    _UiState.value = HomeUiState.Error(it)
                }
            movieRepository.getBasicMovieList(MovieListType.POPULAR)
                .onSuccess { movie ->
                    // _UiState.value = HomeUiState.Success(popularMovies = it)
                    _UiState.update {
                        if (it is HomeUiState.Success) {
                            it.copy(popularMovies = movie)
                        } else {
                            HomeUiState.Success(popularMovies = movie)
                        }
                    }
                }
                .onFailure {
                    _UiState.value = HomeUiState.Error(it)
                }
            movieRepository.getBasicMovieList(MovieListType.TOP_RATED)
                .onSuccess { movie ->
                    //_UiState.value = HomeUiState.Success(topRatedMovies = it)
                    _UiState.update {
                        if (it is HomeUiState.Success) {
                            it.copy(topRatedMovies = movie)
                        } else {
                            HomeUiState.Success(topRatedMovies = movie)
                        }
                    }
                }
                .onFailure {
                    _UiState.value = HomeUiState.Error(it)
                }
        }

    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val nowPlayingMovies: List<BasicMovie> = emptyList(),
        val popularMovies: List<BasicMovie> = emptyList(),
        val topRatedMovies: List<BasicMovie> = emptyList(),
    ) : HomeUiState

    data class Error(val exception: Throwable) : HomeUiState
}