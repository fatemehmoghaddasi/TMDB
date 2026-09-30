package com.example.tmdb.network.model

import com.example.tmdb.model.Cast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCast(
    val name: String,
    val id: Long,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Float,
    @SerialName("profile_path")
    val profilePath: String?,
    val character: String?,
    val order: Int,

    )

fun NetworkCast.mapToCast() = Cast(
    name = name,
    id = id,
    knownForDepartment = knownForDepartment,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath,
    character = character,
    order = order,
)
