package com.example.tmdb.ui.screens.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.ui.componants.MovieItem
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsStateWithLifecycle()

    FavoriteScreen(
        movies = favoriteMovies,
        setIsFavorite = viewModel::setIsFavorite,
        onMovieClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoriteScreen(
    movies: List<BasicMovie>,
    onMovieClick: (Long) -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Favorite Movies")
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        FavoriteContent(
            movies = movies,
            onMovieClick = onMovieClick,
            setIsFavorite = setIsFavorite,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FavoriteContent(
    movies: List<BasicMovie>,
    onMovieClick: (Long) -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                isFavorite = true,
                onMovieClick = onMovieClick,
                setIsFavorite = setIsFavorite
            )
        }
    }
}

@Preview
@Composable
private fun FavoriteScreenPreview() {
    TMDBTheme {
        FavoriteScreen()
    }
}