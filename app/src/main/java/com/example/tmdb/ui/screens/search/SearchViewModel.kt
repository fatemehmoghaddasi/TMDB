package com.example.tmdb.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.model.BasicMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun search(query: String) {
        _uiState.value = SearchUiState.Loading

        viewModelScope.launch {
            movieRepository.search(query)
                .onSuccess {
                    _uiState.value = SearchUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = SearchUiState.Error(it)
                }
        }
    }
}

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val movies: List<BasicMovie>) : SearchUiState
    data class Error(val throwable: Throwable) : SearchUiState
}