package com.example.tmdb.model


data class Cast(
    val name: String,
    val id: Long,
    val knownForDepartment: String,
    val originalName: String,
    val popularity: Float,
    val profilePath: String?,
    val character: String?,
    val order: Int,

    )
