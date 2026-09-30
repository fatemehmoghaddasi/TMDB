package com.example.tmdb.ui.screens.person

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.tmdb.common.UiConstants
import com.example.tmdb.model.BasicMovie
import com.example.tmdb.model.Cast
import com.example.tmdb.ui.screens.person.PersonDetailViewModel.PersonDetailUiState

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel,
    onBackClick: () -> Unit,

    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PersonDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
    )
}

@Composable
private fun PersonDetailScreen(
    uiState: PersonDetailUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        when (uiState) {
            PersonDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is PersonDetailUiState.Success -> {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(uiState.movies) { movie ->
                        PersonMovieItem(
                            movie = movie,
                        )
                    }
                }
            }

            is PersonDetailUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error :${uiState.exception.message}"
                    )
                }
            }
        }

        PersonDetailContent(
            onBackClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp)
        )

    }
}


@Composable
private fun PersonDetailContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onBackClick,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back"
        )
    }

}


@Composable
private fun PersonMovieItem(
    movie: BasicMovie,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
) {

    Column(
        modifier = modifier
            .width(140.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            AsyncImage(
                model = "${UiConstants.POSTER_BASE_URL}${movie.posterPath}",
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .height(210.dp)
                    .width(140.dp)
                    .clip(MaterialTheme.shapes.medium)
            )


        }

        Text(
            text = movie.title,
            autoSize = TextAutoSize.StepBased(maxFontSize = 14.sp),
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            color = contentColor,
        )
    }
}

@Composable
private fun CastImage(
    cast: Cast,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "${UiConstants.POSTER_BASE_URL}${cast.profilePath}",
            contentDescription = "Casts",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = cast.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
    }
}



