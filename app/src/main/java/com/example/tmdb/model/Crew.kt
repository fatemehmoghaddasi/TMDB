package com.example.tmdb.model


data class Crew(
    val name: String,
    val id: Long,
    val knownForDepartment: String,
    val originalName: String,
    val popularity: Float,
    val profilePath: String?,
    val job: String,
)