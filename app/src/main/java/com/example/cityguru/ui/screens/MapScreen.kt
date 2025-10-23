package com.example.cityguru.ui.screens

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cityguru.ui.components.YandexMapComponent
import com.yandex.mapkit.mapview.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onMapViewCreated: (MapView) -> Unit,
    savedInstanceState: Bundle? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CityGuru") }
            )
        }
    ) { paddingValues ->
        YandexMapComponent(
            onMapViewCreated = onMapViewCreated,
            savedInstanceState = savedInstanceState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}