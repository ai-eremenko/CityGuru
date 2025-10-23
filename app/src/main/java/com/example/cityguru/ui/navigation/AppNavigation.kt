package com.example.cityguru.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.mapview.MapView

@Composable
fun AppNavigation(
    onMapViewCreated: (MapView) -> Unit = {}
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        mainGraph(navController, onMapViewCreated)
    }
}