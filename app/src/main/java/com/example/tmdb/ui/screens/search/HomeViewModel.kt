package com.example.tmdb.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.mapToBasicMovie
import com.example.tmdb.network.MovieService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            movieRepository.getNowPlaying()
                .onSuccess {
                    _UiState.value = HomeUiState.Success(it)
                }
                .onFailure {
                    _UiState.value = HomeUiState.Error(it)
                }

        }

    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val movies: List<BasicMovie>) : HomeUiState
    data class Error(val exception: Throwable) : HomeUiState
}