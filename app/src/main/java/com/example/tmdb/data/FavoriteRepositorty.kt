package com.example.tmdb.data

import com.example.tmdb.model.BasicMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteMovies(): Flow<List<BasicMovie>>
    suspend fun insertFavorite(basicMovie: BasicMovie)
    suspend fun deleteFavorite(basicMovie: BasicMovie)
}
