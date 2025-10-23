package com.example.cityguru.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cityguru.ui.screens.CityDetailScreen
import com.example.cityguru.ui.screens.MapScreen
import com.example.cityguru.ui.screens.SearchScreen
import com.yandex.mapkit.mapview.MapView

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    onMapViewCreated: (MapView) -> Unit = {}
) {

    composable(Screen.Search.route) {
        SearchScreen(
            onCityClick = { cityId ->
                navController.navigate(Screen.CityDetail.createRoute(cityId))
            }
        )
    }

    composable(
        route = Screen.CityDetail.route,
        arguments = Screen.CityDetail.arguments
    ) { backStackEntry -> // информация о текущем экране в стеке
        // Извлекаем cityId из аргументов навигации
        val cityId = backStackEntry.arguments?.getInt("cityId") ?: 0
        CityDetailScreen(
            cityId = cityId,
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    composable("map_screen") {
        MapScreen(
            onMapViewCreated = onMapViewCreated
        )
    }
}