package com.example.tmdb.model

data class BasicMovie(
    val id: Long,
    val title: String,
    val originalTitle: String,
    val releaseDate: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val voteAverage: Float,
    val voteCount: Int
)
