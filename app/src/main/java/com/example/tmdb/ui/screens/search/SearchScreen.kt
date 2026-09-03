package com.example.tmdb.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.ui.componants.MovieItem
import com.example.tmdb.ui.screens.favorite.FavoriteViewModel
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteMovies by favoriteViewModel.favoriteMovies.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        favoriteMovies = favoriteMovies,
        search = viewModel::search,
        setIsFavorite = favoriteViewModel::setIsFavorite,
        onMovieClick = {}
    )
}

@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    favoriteMovies: List<BasicMovie>,
    search: (String) -> Unit,
    onMovieClick: (Long) -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF121212))

    ) {
        Image(
            painter = painterResource(id = com.example.tmdb.R.drawable.bg1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            Text(
                text = "What would you like to watch?",
                color = Color.White,
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    search(it)
                },
                placeholder = {
                    Text(
                        text = "Search movies...",
                        color = Color.White.copy(alpha = 0.5f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2A2A2A),
                    unfocusedContainerColor = Color(0xFF2A2A2A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            SearchContent(
                uiState = uiState,
                favoriteMovies = favoriteMovies,
                onMovieClick = onMovieClick,
                setIsFavorite = setIsFavorite,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp)
            )
        }
    }
}

@Composable
private fun SearchContent(
    uiState: SearchUiState,
    favoriteMovies: List<BasicMovie>,
    onMovieClick: (Long) -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        SearchUiState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier,
            )
        }

        SearchUiState.Idle -> {}

        is SearchUiState.Success -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(uiState.movies) { movie ->
                    MovieItem(
                        movie = movie,
                        isFavorite = favoriteMovies.any { it.id == movie.id },
                        onMovieClick = onMovieClick,
                        setIsFavorite = setIsFavorite,
                        contentColor = Color.White,
                    )
                }
            }
        }

        is SearchUiState.Error -> {
            Text(
                text = "Error happened: ${uiState.throwable.message}",
                color = Color.Red,
                modifier = modifier
            )
        }
    }
}

@Composable
@Preview
private fun SearchScreenPreview() {
    TMDBTheme {
        SearchScreen()
    }
}