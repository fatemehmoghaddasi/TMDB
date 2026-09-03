package com.example.tmdb.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb.model.BasicMovie

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Float,
    val voteCount: Int,
    val originalTitle: String
)

fun FavoriteMovieEntity.mapToBasicMovie() = BasicMovie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    originalTitle = originalTitle
)