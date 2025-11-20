package com.example.cityguru.uikit.navigation

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: Int? = null
) {
    object Search : BottomNavItem(
        route = Screen.Search.route,
        title = "Поиск"
    )

    object Map : BottomNavItem(
        route = Screen.Map.route,
        title = "Карта"
    )

    companion object {
        val items = listOf(Search, Map)
    }
}