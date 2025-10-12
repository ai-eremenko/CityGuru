package com.example.cityguru.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cityguru.ui.screens.CityDetailScreen
import com.example.cityguru.ui.screens.SearchScreen

/**
 * Главный граф навигации приложения
 * Определяет все экраны и переходы между ними
 */
fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    // Экран поиска городов
    composable(Screen.Search.route) { // "search" - маршрут к экрану поиска
        SearchScreen(
            onCityClick = { cityId -> // Обработчик клика по городу
                // Переход к деталям города с передачей ID
                navController.navigate(Screen.CityDetail.createRoute(cityId))
            }
        )
    }

    // Экран деталей города
    composable(
        route = Screen.CityDetail.route, // "city_detail/{cityId}" - маршрут с параметром
        arguments = Screen.CityDetail.arguments // Список аргументов для маршрута
    ) { backStackEntry -> // backStackEntry - информация о текущем экране в стеке
        // Извлекаем cityId из аргументов навигации
        val cityId = backStackEntry.arguments?.getInt("cityId") ?: 0
        CityDetailScreen(
            cityId = cityId, // Передаем ID города
            onBackClick = {
                // Возврат на предыдущий экран
                navController.popBackStack()
            }
        )
    }
}