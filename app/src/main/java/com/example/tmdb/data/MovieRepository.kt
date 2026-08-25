package com.example.tmdb.data

import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.MovieListType

interface MovieRepository {
    suspend fun getBasicMovieList(movieListType: MovieListType): Result<List<BasicMovie>>
}
