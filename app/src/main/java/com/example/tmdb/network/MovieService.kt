package com.example.tmdb.network

import com.example.tmdb.model.NetworkNowPlaying
import retrofit2.http.GET
import retrofit2.http.Headers

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(): NetworkNowPlaying
}
