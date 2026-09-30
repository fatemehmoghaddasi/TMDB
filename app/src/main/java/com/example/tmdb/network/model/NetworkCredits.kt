package com.example.tmdb.network.model

import com.example.tmdb.model.Credits
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCredits(
    val cast: List<NetworkCast>,
    val crew: List<NetworkCrew>
)

fun NetworkCredits.mapToCredits() = Credits(
    cast = cast.map { it.mapToCast() },
    crew = crew.map { it.mapToCrew() },
)
