package com.example.cityguru.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object Search : Screen("search")
    object Map : Screen("map_screen")

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