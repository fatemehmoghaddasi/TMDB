package com.example.tmdb.ui.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tmdb.common.UiConstants
import com.example.tmdb.model.BasicMovie

@Composable
fun MovieItem(
    movie: BasicMovie,
    isFavorite: Boolean,
    onMovieClick: (Long) -> Unit,
    setIsFavorite: (Boolean, BasicMovie) -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Column(
        modifier = modifier
            .width(140.dp)
            .clickable { onMovieClick(movie.id) },
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

        Text(
            text = movie.title,
            autoSize = TextAutoSize.StepBased(maxFontSize = 14.sp),
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            color = contentColor,
        )

        Text(
            text = "⭐ ${movie.voteAverage}",
            color = contentColor,
        )
    }
}