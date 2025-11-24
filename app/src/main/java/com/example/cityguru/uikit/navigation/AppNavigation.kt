package com.example.cityguru.uikit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cityguru.feauture.citydetail.CityDetailScreen
import com.example.cityguru.feauture.map.MapScreen
import com.example.cityguru.feauture.search.SearchScreen
import com.yandex.mapkit.mapview.MapView

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in setOf(Screen.Search.route, Screen.Map.route)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Search.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Search.route) {
                SearchScreen(navController = navController)
            }

            composable(Screen.Map.route) {
                MapScreen(
                    onMapViewCreated = { },
                    navController = navController
                )
            }

            composable(
                route = Screen.CityDetail.route,
                arguments = Screen.CityDetail.arguments
            ) { backStackEntry ->
                val cityId = backStackEntry.arguments?.getInt("cityId") ?: 0
                CityDetailScreen(
                    cityId = cityId,
                    navController = navController
                )
            }
        }
    }
}

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Search : Screen("search")
    object Map : Screen("map")

    object CityDetail : Screen(
        route = "city_detail/{cityId}",
        arguments = listOf(
            navArgument("cityId") {
                type = NavType.IntType
            }
        )
    ) {
        fun createRoute(cityId: Int) = "city_detail/$cityId"
    }
}