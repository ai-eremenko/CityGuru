package com.example.cityguru.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * sealed class для определения всех экранов приложения
 * Помогает избежать ошибок в строках-маршрутах
 */
sealed class Screen(
    val route: String, // Маршрут экрана
    val arguments: List<NavArgument> = emptyList() // Аргументы для маршрута
) {
    // Экран поиска городов
    object Search : Screen("search")

    // Экран деталей города
    object CityDetail : Screen(
        route = "city_detail/{cityId}", // Маршрут с параметром cityId
        arguments = listOf(
            navArgument("cityId") { // Определение аргумента
                type = NavType.IntType // Тип аргумента - Int
            }
        )
    ) {
        /**
         * Создает маршрут с переданным cityId
         * @param cityId ID города для отображения деталей
         */
        fun createRoute(cityId: Int) = "city_detail/$cityId"
    }
}