package com.example.tmdb.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.tmdb.common.UiConstants

@Composable
fun MovieDetailScreen(
    movieId: Long,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.getMovieById(movieId)
    }
    MovieDetailScreen(
        uiState = uiState,
    )
}


@Composable
private fun MovieDetailScreen(
    uiState: DetailUiState,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailUiState.Success -> {
                val movie = uiState.movie

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(40.dp))

                    AsyncImage(
                        model = "${UiConstants.POSTER_BASE_URL}${movie.posterPath}",
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .height(240.dp)
                            .width(160.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text = movie.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = movie.originalTitle,
                            fontSize = 16.sp
                        )

                        Text(
                            text = "⭐ ${movie.voteAverage}",
                            fontSize = 18.sp
                        )

                        Text(
                            text = "Release date: ${movie.releaseDate ?: "Unknown"}"
                        )

                        Text(
                            text = "Votes: ${movie.voteCount}"
                        )

                        Text(
                            text = movie.overview,
                            fontSize = 16.sp
                        )
                    }
                }
            }


            is DetailUiState.Error -> {
                Text(
                    text = "Error happened",
                    color = Color.Red
                )
            }
        }
    }
}
