package com.example.tmdb.data

import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.Credits
import com.example.tmdb.model.MovieListType

interface MovieRepository {
    suspend fun getBasicMovieList(movieListType: MovieListType): Result<List<BasicMovie>>

    suspend fun search(query: String): Result<List<BasicMovie>>

    suspend fun getMovieById(id: Long): Result<BasicMovie>

    suspend fun getMovieCredits(id: Long): Result<Credits>

    suspend fun getPersonMovieCredits(id: Long): Result<List<BasicMovie>>
}
