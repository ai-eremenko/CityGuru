package com.example.cityguru.ui.screens
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.cityguru.ui.navigation.AppNavigation
import com.example.cityguru.ui.navigation.BottomNavItem
import com.example.cityguru.ui.navigation.BottomNavigationBar
import com.example.cityguru.ui.navigation.currentRoute
import com.yandex.mapkit.mapview.MapView

@Composable
fun MainScreen(
    onMapViewCreated: (MapView) -> Unit = {}
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val currentRoute = currentRoute(navController)
            if (currentRoute in BottomNavItem.items.map { it.route }) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            innerPadding = innerPadding,
            onMapViewCreated = onMapViewCreated
        )
    }
}