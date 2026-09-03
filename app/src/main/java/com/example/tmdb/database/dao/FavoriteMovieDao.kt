package com.example.tmdb.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdb.database.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    @Insert
    suspend fun insertFavorite(favoriteMovieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavorite(favoriteMovieEntity: FavoriteMovieEntity)
}
