package com.example.tmdb.network

import com.example.tmdb.network.model.NetworkBasicMovie
import com.example.tmdb.network.model.NetworkBasicMovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(): NetworkBasicMovieList

    @GET("movie/popular")
    suspend fun getPopularMovies(): NetworkBasicMovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): NetworkBasicMovieList

    @GET("search/movie")
    suspend fun search(@Query("query") query: String): NetworkBasicMovieList

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Long): NetworkBasicMovie
}
