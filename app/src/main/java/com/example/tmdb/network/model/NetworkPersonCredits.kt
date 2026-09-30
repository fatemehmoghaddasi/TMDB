package com.example.tmdb.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPersonCredits(
    val cast: List<NetworkBasicMovie>,
    val crew: List<NetworkBasicMovie>
)

fun NetworkPersonCredits.mapToBasicMovie() = cast.map { it.mapToBasicMovie() }

