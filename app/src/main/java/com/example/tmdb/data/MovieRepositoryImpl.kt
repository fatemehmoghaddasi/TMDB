package com.example.tmdb.data

import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.MovieListType
import com.example.tmdb.network.MovieService
import com.example.tmdb.network.model.mapToBasicMovie
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

    override suspend fun search(query: String): Result<List<BasicMovie>> {
        return runCatching {
            movieService.search(query).results.map {
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





