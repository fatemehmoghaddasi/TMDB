package com.example.tmdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.database.dao.FavoriteMovieDao
import com.example.tmdb.database.model.FavoriteMovieEntity

@Database(
    entities = [
        FavoriteMovieEntity::class,
    ],
    version = 1,
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
