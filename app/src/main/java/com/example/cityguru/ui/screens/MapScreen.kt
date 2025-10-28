package com.example.cityguru.ui.screens

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
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
    YandexMapComponent(
        onMapViewCreated = onMapViewCreated,
        savedInstanceState = savedInstanceState,
        modifier = Modifier.fillMaxSize()
        )
}