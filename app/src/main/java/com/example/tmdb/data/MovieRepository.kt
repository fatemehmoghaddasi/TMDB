package com.example.tmdb.data

import com.example.tmdb.model.BasicMovie

interface MovieRepository {
    suspend fun getNowPlaying(): Result<List<BasicMovie>>
}
