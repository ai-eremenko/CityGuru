package com.example.cityguru.feauture.map.view

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cityguru.domain.model.City
import com.example.cityguru.utils.addCitiesFromApiData
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView

@Composable
fun YandexMapComponent(
    onMapRegionChanged: (Point, Float) -> Unit,
    onMapViewCreated: (MapView) -> Unit,
    savedInstanceState: Bundle?,
    modifier: Modifier = Modifier,
    cities: List<City> = emptyList(),
    onCitySelected: (City) -> Unit = {}
) {
    var mapViewHolder by remember { mutableStateOf<MapView?>(null) }

    LaunchedEffect(cities) {
        val mapView = mapViewHolder ?: return@LaunchedEffect
        mapView.map.mapObjects.clear()
        if (cities.isNotEmpty()) {
            mapView.map.mapObjects.addCitiesFromApiData(cities, onCitySelected, mapView.context)
        }
    }

    AndroidView(
        factory = { context ->
            val mapView = MapView(context).apply {
                map.isRotateGesturesEnabled = true
                map.isZoomGesturesEnabled = true
                map.isScrollGesturesEnabled = true
                map.setNightModeEnabled(false)

                map.addCameraListener(object : CameraListener {
                    override fun onCameraPositionChanged(
                        p0: Map,
                        cameraPosition: CameraPosition,
                        cameraUpdateReason: CameraUpdateReason,
                        finished: Boolean
                    ) {
                        Log.d(
                            "MAP_CAMERA_DEBUG", "Camera changed: finished=$finished, " +
                                    "zoom=${cameraPosition.zoom}, target=${cameraPosition.target}"
                        )
                        if (finished) {
                            onMapRegionChanged(
                                cameraPosition.target,
                                cameraPosition.zoom
                            )
                        }
                    }
                })

                onMapViewCreated(this)
            }
            mapViewHolder = mapView
            mapView
        },
        update = { mapView ->
        },
        modifier = modifier
    )
}