package com.example.tmdb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.ui.screens.search.SearchScreen
import com.example.tmdb.ui.screens.SplashScreen
import com.example.tmdb.ui.screens.search.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun TmdbNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashRoute
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
        composable<SearchRoute>{
            SearchScreen()
        }
        composable<HomeRoute>{
            HomeScreen()
        }
    }
}

@Serializable
data object SplashRoute
@Serializable
data object SearchRoute
@Serializable
data object HomeRoute