package com.example.cityguru.ui.navigation

// navigation/NavGraph.kt
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "city_list"
                ) {
                    mainGraph(navController)
                }
            }
        }
    }
}
