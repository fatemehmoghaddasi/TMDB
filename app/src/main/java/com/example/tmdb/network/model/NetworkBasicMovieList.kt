package com.example.tmdb.network.model

import com.example.tmdb.model.BasicMovie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class NetworkBasicMovieList(
    val results : List<NetworkBasicMovie>,
    val page : Int,
    @SerialName("total_pages")
    val totalPages : Int,
    @SerialName("total_results")
    val totalResults : Int,
)
@Serializable
data class NetworkBasicMovie(
    val id : Long,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val title : String,
    @SerialName("original_title")
    val originalTitle : String,
    val overview : String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("vote_average")
    val voteAverage : Float,
    @SerialName("vote_count")
    val voteCount : Int
)

fun NetworkBasicMovie.mapToBasicMovie() = BasicMovie(
    id = id,
    title = title,
    originalTitle = originalTitle,
    releaseDate = releaseDate,
    overview = overview,
    backdropPath = backdropPath,
    posterPath = posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount
)

