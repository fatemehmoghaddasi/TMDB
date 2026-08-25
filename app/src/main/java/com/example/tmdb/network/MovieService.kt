package com.example.tmdb.network

import com.example.tmdb.model.NetworkBasicMovieList
import retrofit2.http.GET

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(): NetworkBasicMovieList

    @GET("movie/popular")
    suspend fun getPopularMovies(): NetworkBasicMovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): NetworkBasicMovieList
}
