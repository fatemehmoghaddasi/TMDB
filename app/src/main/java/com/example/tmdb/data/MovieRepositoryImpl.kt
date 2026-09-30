package com.example.tmdb.data

import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.Credits
import com.example.tmdb.model.MovieListType
import com.example.tmdb.network.MovieService
import com.example.tmdb.network.model.mapToBasicMovie
import com.example.tmdb.network.model.mapToCredits
import jakarta.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {

    override suspend fun getBasicMovieList(movieListType: MovieListType): Result<List<BasicMovie>> {
        return runCatching {
            when (movieListType) {
                MovieListType.NOW_PLAYING -> movieService.getNowPlaying().results.map {
                    it.mapToBasicMovie()
                }

                MovieListType.POPULAR -> movieService.getPopularMovies().results.map {
                    it.mapToBasicMovie()
                }

                MovieListType.TOP_RATED -> movieService.getTopRatedMovies().results.map {
                    it.mapToBasicMovie()
                }
            }
        }
    }

    override suspend fun getPersonMovieCredits(id: Long): Result<List<BasicMovie>> {
        return runCatching {
            movieService.getPersonMovieCredits(id).cast.map { it.mapToBasicMovie() }
        }
    }

    override suspend fun getMovieCredits(id: Long): Result<Credits> {
        return runCatching {
            movieService.getMovieById(id).credits!!.mapToCredits()
        }
    }

    override suspend fun search(query: String): Result<List<BasicMovie>> {
        return runCatching {
            movieService.search(query).results.map {
                it.mapToBasicMovie()
            }
        }
    }

    override suspend fun getMovieById(id: Long): Result<BasicMovie> {
        return runCatching {
            movieService.getMovieById(id).mapToBasicMovie()
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





