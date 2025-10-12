package com.example.cityguru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.cityguru.ui.navigation.AppNavigation
import com.example.cityguru.ui.theme.CityGuruTheme

/**
 * Главная Activity приложения
 * Устанавливает Compose как основной UI
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем Compose контент
        setContent {
            // Тема приложения
            CityGuruTheme {
                // Основная поверхность (фон)
                Surface(
                    modifier = Modifier.fillMaxSize(), // Занимает весь экран
                    color = MaterialTheme.colorScheme.background // Цвет фона из темы
                ) {
                    // Запускаем навигацию приложения
                    AppNavigation()
                }
            }
        }
    }
}