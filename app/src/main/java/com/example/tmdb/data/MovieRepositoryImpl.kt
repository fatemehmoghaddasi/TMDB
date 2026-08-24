package com.example.tmdb.data

import android.util.Log.e
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.mapToBasicMovie
import com.example.tmdb.network.MovieService
import jakarta.inject.Inject
import kotlinx.coroutines.channels.ChannelResult.Companion.success
import retrofit2.Response.success
import kotlin.Result.Companion.success


class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {

    override suspend fun getNowPlaying(): Result<List<BasicMovie>> {
        return runCatching {
            movieService.getNowPlaying().results.map {
                it.mapToBasicMovie()
            }
        }
    }
}

        /*try {
            Result.success(movieService.getNowPlaying().results.map {
                it.mapToBasicMovie()
            }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }*/





