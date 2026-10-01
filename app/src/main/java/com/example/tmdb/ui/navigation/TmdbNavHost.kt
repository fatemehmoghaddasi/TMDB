package com.example.tmdb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.tmdb.ui.screens.SplashScreen
import com.example.tmdb.ui.screens.details.DetailScreen
import com.example.tmdb.ui.screens.details.DetailViewModel
import com.example.tmdb.ui.screens.favorite.FavoriteScreen
import com.example.tmdb.ui.screens.home.HomeScreen
import com.example.tmdb.ui.screens.person.PersonDetailScreen
import com.example.tmdb.ui.screens.person.PersonDetailViewModel
import com.example.tmdb.ui.screens.search.SearchScreen
import kotlinx.serialization.Serializable

@Composable
fun TmdbNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        modifier = modifier,
    ) {
        composable<SplashRoute> {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(HomeRoute) {
                        popUpTo(SplashRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<SearchRoute> {
            SearchScreen(
                onMovieClick = {
                    navController.navigate(DetailRoute(it.id))
                }
            )
        }
        composable<HomeRoute> {
            HomeScreen(
                onMovieClick = {
                    navController.navigate(DetailRoute(it.id))
                },
                onSearchClick = { navController.navigate(SearchRoute) }
            )
        }
        composable<FavoriteRoute> {
            FavoriteScreen()
        }
        composable<DetailRoute> { backStackEntry ->
            val movieId = backStackEntry.toRoute<DetailRoute>()
            DetailScreen(
                viewModel = hiltViewModel<DetailViewModel, DetailViewModel.DetailViewModelFactory>(
                    creationCallback = {
                        it.create(
                            movieId.id
                        )
                    }
                ),
                onBackClick = { navController.popBackStack() },
                onSearchClick = { navController.navigate(SearchRoute) },
                onPersonClick = { cast ->
                    navController.navigate(
                        PersonDetailRoute(
                            personId = cast.id,
                            name = cast.name,
                            profilePath = cast.profilePath
                        )
                    )
                }

            )
        }
        composable<PersonDetailRoute> { backStackEntry ->
            val person = backStackEntry.toRoute<PersonDetailRoute>()
            PersonDetailScreen(
                viewModel = hiltViewModel<PersonDetailViewModel, PersonDetailViewModel.PersonDetailViewModelFactory>(
                    creationCallback = { it.create(person.personId) }
                ),
                onBackClick = { navController.popBackStack() },
                name = person.name,
                profilePath = person.profilePath
            )
        }
    }
}


@Serializable
data object SplashRoute

@Serializable
data object SearchRoute

@Serializable
data object HomeRoute

@Serializable
data object FavoriteRoute

@Serializable
data class DetailRoute(val id: Long)

@Serializable
data class PersonDetailRoute(
    val personId: Long,
    val name: String,
    val profilePath: String?,
)