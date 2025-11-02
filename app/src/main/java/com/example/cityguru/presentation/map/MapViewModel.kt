package com.example.cityguru.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguru.domain.map.MapInteractor
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(
    private val mapInteractor: MapInteractor
) : ViewModel() {

    private companion object {
        const val TAG = "MapViewModel"
    }

    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    private var currentCenter: Point = Point(55.751574, 37.573856)
    private var currentZoom: Float = 5.0f

    fun onMapRegionChanged(center: Point, zoom: Float) {
        currentCenter = center
        currentZoom = zoom

        if (zoom >= 17) {
            loadNearbyCities(center)
        } else {
            _state.update { it.copy(cities = emptyList()) }
        }
    }

    private fun loadNearbyCities(center: Point) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val radius = calculateRadius(currentZoom)

                val cities = mapInteractor.getNearbyCities(
                    lat = center.latitude,
                    lng = center.longitude,
                    radius = radius
                )

                _state.update {
                    it.copy(
                        cities = cities,
                        isLoading = false,
                        error = null
                    )
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка загрузки городов: ${e.message}"
                    )
                }
            }
        }
    }

    private fun calculateRadius(zoom: Float): Int {
        return when {
            zoom >= 19 -> 10000  // 10km
            zoom >= 18 -> 20000  // 20km
            zoom >= 17 -> 50000  // 50km
            else -> 50000
        }
    }

    fun refreshCities() {
        if (currentZoom >= 17) {
            loadNearbyCities(currentCenter)
        }
    }
}
