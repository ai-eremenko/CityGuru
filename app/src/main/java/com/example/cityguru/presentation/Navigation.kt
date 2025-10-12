package com.example.cityguru.presentation

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "cities"
    ) {
        composable("cities") {
            CitiesScreen { cityId ->
                navController.navigate("cityDetail/$cityId")
            }
        }
        composable("cityDetail/{cityId}") { backStackEntry ->
            val cityId = backStackEntry.arguments?.getString("cityId")?.toIntOrNull() ?: 0
            CityDetailScreen(
                cityId = cityId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}