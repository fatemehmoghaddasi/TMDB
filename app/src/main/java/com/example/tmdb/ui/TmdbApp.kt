package com.example.tmdb.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.ui.componants.BottomBar
import com.example.tmdb.ui.navigation.DetailRoute
import com.example.tmdb.ui.navigation.FavoriteRoute
import com.example.tmdb.ui.navigation.HomeRoute
import com.example.tmdb.ui.navigation.TmdbNavHost

@Composable
fun TmdbApp() {
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
        },
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->

        TmdbNavHost(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}