package com.example.tmdb.ui.screens.search

import androidx.lifecycle.ViewModel
import com.example.tmdb.network.MovieService
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieService: MovieService
): ViewModel(){

}