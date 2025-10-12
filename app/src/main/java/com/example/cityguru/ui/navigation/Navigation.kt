package com.example.cityguru.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.cityguru.ui.screens.CityDetailScreen
import com.example.cityguru.ui.screens.SearchScreen

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable("search") {
        SearchScreen(
            onCityClick = { cityId ->
                navController.navigate("city_detail/$cityId")
            }
        )
    }

    composable("city_detail/{cityId}") { backStackEntry ->
        val cityId = backStackEntry.arguments?.getString("cityId")?.toIntOrNull() ?: 0
        CityDetailScreen(
            cityId = cityId,
            onBackClick = { navController.popBackStack() }
        )
    }
}

// MainActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost

// presentation/navigation/Navigation.kt
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Cities.route
    ) {
        composable(Screen.Cities.route) {
            // Koin автоматически инжектит ViewModel
            val viewModel: CitiesViewModel by viewModel()
            CitiesScreen(
                viewModel = viewModel,
                onCityClick = { cityId ->
                    navController.navigate(Screen.CityDetail.createRoute(cityId))
                }
            )
        }
        composable(
            route = Screen.CityDetail.route,
            arguments = listOf(navArgument("cityId") { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel: CityDetailViewModel by viewModel()
            val cityId = backStackEntry.arguments?.getInt("cityId") ?: 0

            CityDetailScreen(
                viewModel = viewModel,
                cityId = cityId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
