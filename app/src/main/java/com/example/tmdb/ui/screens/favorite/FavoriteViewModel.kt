package com.example.tmdb.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.FavoriteRepository
import com.example.tmdb.model.BasicMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    val favoriteMovies: StateFlow<List<BasicMovie>> = favoriteRepository.getFavoriteMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun setIsFavorite(isFavorite: Boolean, movie: BasicMovie) {
        if (isFavorite) {
            insertFavorite(movie)
        } else {
            deleteFavorite(movie)
        }
    }

    fun insertFavorite(basicMovie: BasicMovie) {
        viewModelScope.launch {
            favoriteRepository.insertFavorite(basicMovie)
        }
    }

    fun deleteFavorite(basicMovie: BasicMovie) {
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(basicMovie)
        }
    }
}
