package com.example.tmdb.ui.componants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tmdb.ui.navigation.FavoriteRoute
import com.example.tmdb.ui.navigation.HomeRoute


@Composable
fun BottomBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    NavigationBar() {
        NavigationBarItem(
            selected = currentDestination?.hasRoute<HomeRoute>() == true,
            onClick = {
                navController.navigate(HomeRoute) {
                    popUpTo(HomeRoute) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            },
            label = {
                Text("Home")
            }
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute<FavoriteRoute>() == true,
            onClick = {
                navController.navigate(FavoriteRoute) {
                    popUpTo(HomeRoute) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorites")
            },
            label = {
                Text("Favorites")
            }
        )
    }
}
