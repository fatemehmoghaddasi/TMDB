package com.example.tmdb.data

import com.example.tmdb.database.dao.FavoriteMovieDao
import com.example.tmdb.database.model.mapToBasicMovie
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.mapToMovieEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl @Inject constructor(private val favoriteMovieDao: FavoriteMovieDao) :
    FavoriteRepository {
    override fun getFavoriteMovies(): Flow<List<BasicMovie>> {
        return favoriteMovieDao.getFavoriteMovies()
            .map { entities ->
                entities.map {
                    it.mapToBasicMovie()
                }
            }

    }

    override suspend fun insertFavorite(basicMovie: BasicMovie) {
        return favoriteMovieDao.insertFavorite(basicMovie.mapToMovieEntity())
    }

    override suspend fun deleteFavorite(basicMovie: BasicMovie) {
        return favoriteMovieDao.deleteFavorite(basicMovie.mapToMovieEntity())
    }
}