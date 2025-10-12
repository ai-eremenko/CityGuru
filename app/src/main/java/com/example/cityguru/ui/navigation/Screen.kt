package com.example.cityguru.ui.navigation

sealed class Screen(val route: String) {
    object Cities : Screen("cities")
    object CityDetail : Screen("city_detail/{cityId}") {
        fun createRoute(cityId: Int) = "city_detail/$cityId"
    }
}