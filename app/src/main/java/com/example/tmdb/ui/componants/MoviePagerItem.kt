package com.example.tmdb.ui.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmdb.common.UiConstants
import com.example.tmdb.model.BasicMovie

@Composable
fun MoviePagerItem(
    movie: BasicMovie,
    isFavorite: Boolean,
    onMovieClick: (BasicMovie) -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onMovieClick(movie) },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            AsyncImage(
                model = "${UiConstants.BACKDROP_BASE_URL}${movie.backdropPath}",
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )

            IconButton(
                onClick = {
                    if (isFavorite) {
                        setIsFavorite(false, movie)
                    } else {
                        setIsFavorite(true, movie)
                    }
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    },
                    contentDescription = "Likes Status",
                    tint = Color.Red
                )
            }
        }
    }
}
