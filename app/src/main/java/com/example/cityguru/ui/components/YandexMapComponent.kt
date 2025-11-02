package com.example.cityguru.ui.components

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cityguru.domain.model.City
import com.example.cityguru.utils.addCitiesFromApiData
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

@Composable
fun YandexMapComponent(
    onMapViewCreated: (MapView) -> Unit,
    savedInstanceState: Bundle?,
    modifier: Modifier = Modifier,
    cities: List<City> = emptyList(),
    onCitySelected: (City) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            MapView(context).apply {
                map.isRotateGesturesEnabled = true
                map.isZoomGesturesEnabled = true
                map.isScrollGesturesEnabled = true
                map.setNightModeEnabled(false)

                if (cities.isNotEmpty()) {
                    map.mapObjects.addCitiesFromApiData(cities, onCitySelected, context)
                }
                onMapViewCreated(this)

                map.move(
                    CameraPosition(
                        Point(55.751574, 37.573856),
                        17.0f, 0.0f, 0.0f
                    )
                )
            }
        },
        modifier = modifier
    )
}