package com.example.tmdb.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.tmdb.common.UiConstants
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.Cast
import com.example.tmdb.model.Credits

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onPersonClick = onPersonClick,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreen(
    uiState: DetailUiState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        when (uiState) {
            DetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailUiState.Success -> {
                MovieDetailContent(
                    movie = uiState.movie,
                    credits = uiState.credits,
                    onBackClick = onBackClick,
                    onSearchClick = onSearchClick,
                    onPersonClick = onPersonClick,
                )


            }

            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error happened",
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieDetailContent(
    movie: BasicMovie,
    credits: Credits,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPersonClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        MovieHeader(
            movie = movie,
            onBackClick = onBackClick,
            onSearchClick = onSearchClick,
        )
        MovieInfo(
            movie = movie
        )
        Spacer(modifier = Modifier.height(16.dp))

        Casts(
            cast = credits.cast,
            onPersonClick = onPersonClick,
        )
    }

}

@Composable
private fun MovieHeader(
    movie: BasicMovie,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,

    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        AsyncImage(
            model = "${UiConstants.POSTER_BASE_URL}${movie.backdropPath}",
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()

        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.45f to Color.Transparent,
                        0.65f to Color(0x33171827),
                        0.8f to Color(0xAA171827),
                        1f to Color(0xFF171827)
                    )
                )
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 32.dp, start = 8.dp
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        IconButton(
            onClick = { onSearchClick() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 32.dp, start = 8.dp

                )
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }

        Text(
            text = movie.title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        )
    }
}

@Composable
private fun MovieInfo(
    movie: BasicMovie,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Top Build Cast",

            )
    }
}

@Composable
private fun Casts(
    cast: List<Cast>,
    onPersonClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)

    ) {
        items(cast) { cast ->

            Column(
                modifier = Modifier
                    .width(100.dp)
                    .clickable { onPersonClick(cast.id) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "${UiConstants.POSTER_BASE_URL}${cast.profilePath}",
                    contentDescription = "Casts",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(
                            RoundedCornerShape(16.dp),

                            )

                )
                Text(
                    text = cast.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}















