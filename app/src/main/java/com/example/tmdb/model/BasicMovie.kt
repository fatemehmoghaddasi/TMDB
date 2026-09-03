package com.example.tmdb.model

import com.example.tmdb.database.model.FavoriteMovieEntity

data class BasicMovie(
    val id: Long,
    val title: String,
    val originalTitle: String,
    val releaseDate: String?,
    val overview: String,
    val backdropPath: String?,
    val posterPath: String?,
    val voteAverage: Float,
    val voteCount: Int
)

fun BasicMovie.mapToMovieEntity() = FavoriteMovieEntity(
    id = id,
    title = title,
    originalTitle = originalTitle,
    releaseDate = releaseDate ?: "",
    overview = overview,
    backdropPath = backdropPath,
    posterPath = posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount
)