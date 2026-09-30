package com.example.tmdb.network.model

import com.example.tmdb.model.Crew
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCrew(
    val name: String,
    val id: Long,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Float,
    @SerialName("profile_path")
    val profilePath: String?,
    val job: String,
)

fun NetworkCrew.mapToCrew() = Crew(
    name = name,
    id = id,
    knownForDepartment = knownForDepartment,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath,
    job = job
)

