package com.example.cityguru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.cityguru.uikit.navigation.AppNavigation
import com.example.cityguru.uikit.theme.CityGuruTheme
import com.yandex.mapkit.MapKitFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setLocale("ru_RU")
        MapKitFactory.initialize(this)

        setContent {
            CityGuruTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}