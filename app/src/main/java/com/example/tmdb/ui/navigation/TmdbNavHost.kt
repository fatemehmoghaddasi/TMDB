package com.example.tmdb.ui.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.ui.componants.BottomBar
import com.example.tmdb.ui.screens.SplashScreen
import com.example.tmdb.ui.screens.details.MovieDetailScreen
import com.example.tmdb.ui.screens.favorite.FavoriteScreen
import com.example.tmdb.ui.screens.home.HomeScreen
import com.example.tmdb.ui.screens.search.SearchScreen
import kotlinx.serialization.Serializable

@Composable
fun TmdbNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar =
        currentDestination?.hasRoute<HomeRoute>() == true ||
                currentDestination?.hasRoute<FavoriteRoute>() == true ||
                currentDestination?.hasRoute<DetailRoute>() == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = SplashRoute,
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
                SearchScreen()
            }
            composable<HomeRoute> {
                HomeScreen(
                    onMovieClick = {
                        navController.navigate(DetailRoute(it))
                    },
                    onSearchClick = { navController.navigate(SearchRoute) }
                )
            }
            composable<FavoriteRoute> {
                FavoriteScreen()
            }
            composable<DetailRoute> {
                MovieDetailScreen()
            }
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