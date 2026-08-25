package com.example.tmdb.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.tmdb.common.UiConstants
import com.example.tmdb.model.BasicMovie

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    Scaffold() { innerPadding ->
        when (uiState) {
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }


            is HomeUiState.Success -> {
                HomeContent(
                    uiState = uiState,
                    modifier = modifier
                        .padding(innerPadding)
                )
            }


            is HomeUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Error happened", color = Color.Red
                    )
                }
            }
        }
    }
}


@Composable
private fun HomeContent(
    uiState: HomeUiState.Success,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {


        MovieListRow(
            title = "Now Playing",
            movies = uiState.movies,

            )
        MovieListRow(
            title = "Popular Movies",
            movies = uiState.popularMovies,
        )
        MovieListRow(
            title = "Top Rated",
            movies = uiState.topRatedMovies,
        )
    }
}

@Composable
fun MovieListRow(
    title: String,
    movies: List<BasicMovie>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()

    ) {
        Text(text = title)
        LazyRow(modifier = modifier.fillMaxWidth()) {
            items(movies) { movies ->
                MovieItem(movies)
            }
        }
    }
}

@Composable
private fun MovieItem(
    movies: BasicMovie,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
           model = "${ UiConstants.POSTER_BASE_URL}${movies.posterPath}",
            contentDescription = "Movie Poster"
        )
        Text(movies.title)
    }
}
