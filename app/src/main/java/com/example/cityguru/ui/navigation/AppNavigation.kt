package com.example.cityguru.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.mapview.MapView

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues = PaddingValues(),
    onMapViewCreated: (MapView) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        mainGraph(navController, onMapViewCreated)
    }
}