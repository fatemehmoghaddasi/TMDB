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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.ui.componants.MovieItem
import com.example.tmdb.ui.screens.favorite.FavoriteViewModel


@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit,
    onSearchClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteMovies by favoriteViewModel.favoriteMovies.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        favoriteMovies = favoriteMovies,
        onMovieClick = onMovieClick,
        onSearchClick = onSearchClick,
        setIsFavorite = favoriteViewModel::setIsFavorite
//        setIsFavorite = { isFavorite, movie ->
//            favoriteViewModel.setIsFavorite(isFavorite, movie)
//        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    favoriteMovies: List<BasicMovie>,
    onMovieClick: (Long) -> Unit,
    onSearchClick: () -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home")
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        },
        modifier = modifier,
    ) { innerPadding ->
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
                    favoriteMovies = favoriteMovies,
                    modifier = Modifier.padding(innerPadding),
                    onMovieClick = onMovieClick,
                    setIsFavorite = setIsFavorite,
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
    favoriteMovies: List<BasicMovie>,
    onMovieClick: (Long) -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MovieListRow(
            title = "Now Playing",
            movies = uiState.nowPlayingMovies,
            favoriteMovies = favoriteMovies,
            onMovieClick = onMovieClick,
            setIsFavorite = setIsFavorite
        )

        MovieListRow(
            title = "Popular Movies",
            movies = uiState.popularMovies,
            favoriteMovies = favoriteMovies,
            onMovieClick = onMovieClick,
            setIsFavorite = setIsFavorite
        )

        MovieListRow(
            title = "Top Rated",
            movies = uiState.topRatedMovies,
            favoriteMovies = favoriteMovies,
            onMovieClick = onMovieClick,
            setIsFavorite = setIsFavorite
        )
    }
}

@Composable
fun MovieListRow(
    movies: List<BasicMovie>,
    title: String,
    favoriteMovies: List<BasicMovie>,
    onMovieClick: (Long) -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    isFavorite = favoriteMovies.any { it.id == movie.id },
                    onMovieClick = onMovieClick,
                    setIsFavorite = setIsFavorite
                )
            }
        }
    }
}
