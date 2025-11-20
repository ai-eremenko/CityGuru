package com.example.cityguru.feauture.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguru.domain.map.MapInteractor
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(
    private val mapInteractor: MapInteractor
) : ViewModel() {

    init {
        Log.d("MAPVM_DEBUG", "✅ MapViewModel created with interactor: $mapInteractor")
    }

    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    private var currentCenter: Point = Point(55.7558, 37.6173)
    private var currentZoom: Float = 10.0f

    private var loadCitiesJob: Job? = null
    private var lastProcessedCenter: Point? = null
    private var lastProcessedZoom: Float? = null

    fun onMapRegionChanged(center: Point, zoom: Float) {
        currentCenter = center
        currentZoom = zoom

        loadCitiesJob?.cancel()

        loadCitiesJob = viewModelScope.launch {
            loadCitiesJob?.join()
            delay(300) // Только дебаунс, без сложной логики

            if (isSignificantChange(center, zoom)) {
                lastProcessedCenter = center
                lastProcessedZoom = zoom
                handleRegionChange(center, zoom)
            } else {
                Log.d("MAPVM_DEBUG", "⏭️  Незначительное изменение - пропускаем запрос")
            }
        }
    }

    private fun isSignificantChange(newCenter: Point, newZoom: Float): Boolean {
        val lastCenter = lastProcessedCenter ?: return true // Первый запрос
        val lastZoom = lastProcessedZoom ?: return true

        // Проверяем только зум (простая логика)
        val zoomDiff = Math.abs(newZoom - lastZoom)
        val distance = calculateDistance(lastCenter, newCenter)
        return zoomDiff >= 0.2f || distance >= 20.0 // Увеличил порог до 1.0 для меньшего количества запросов

    }

    private fun calculateDistance(point1: Point, point2: Point): Double {
        val earthRadius = 6371.0 // km

        val dLat = Math.toRadians(point2.latitude - point1.latitude)
        val dLon = Math.toRadians(point2.longitude - point1.longitude)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(point1.latitude)) * Math.cos(Math.toRadians(point2.latitude)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c
    }

    private suspend fun handleRegionChange(center: Point, zoom: Float) {
        Log.d("MAPVM_DEBUG", "🔄 handleRegionChange - center: (${center.latitude}, ${center.longitude}), zoom: $zoom")

        // Обновляем последние обработанные значения
        lastProcessedCenter = center
        lastProcessedZoom = zoom

        if (zoom <= 10.0f) {
            Log.d("MAPVM_DEBUG", "🗺  Zoom $zoom <= 10 - загружаем города")
            loadNearbyCities(center, zoom)
        } else {
            Log.d("MAPVM_DEBUG", "🔍 Zoom $zoom > 10 - очищаем список городов")
            _state.update { it.copy(cities = emptyList()) }
        }
    }

    private suspend fun loadNearbyCities(center: Point, zoom: Float) {
        Log.d("MAPVM_DEBUG", "📍 loadNearbyCities - START")

        val radius = calculateRadius(zoom)
        Log.d("MAPVM_DEBUG", "🎯 Параметры запроса: " +
                "lat=${center.latitude}, lng=${center.longitude}, radius=$radius км, zoom=$zoom")

        val requestId = "${center.latitude}_${center.longitude}_${radius}_${System.currentTimeMillis()}"
        Log.d("MAPVM_DEBUG", "🎯 Параметры запроса: " +
                "lat=${center.latitude}, lng=${center.longitude}, radius=$radius км, zoom=$zoom, requestId=$requestId")

        try {
            _state.update { it.copy(isLoading = true, error = null) }
            Log.d("MAPVM_DEBUG", "⏳ Состояние обновлено: isLoading=true")

            val cities = mapInteractor.getNearbyCities(
                lat = center.latitude,
                lng = center.longitude,
                radius = radius,
                requestId = requestId // Добавьте этот параметр в метод getNearbyCities
            )

            Log.d("MAPVM_DEBUG", "✅ УСПЕХ: Загружено ${cities.size} городов")
            cities.forEachIndexed { index, city ->
                Log.d("MAPVM_DEBUG", "   ${index + 1}. ${city.name} (${city.point.latitude}, ${city.point.longitude})")
            }

            _state.update {
                it.copy(
                    cities = cities,
                    isLoading = false,
                    error = null
                )
            }
            Log.d("MAPVM_DEBUG", "🎉 Состояние обновлено: cities=${cities.size}, isLoading=false")

        } catch (e: Exception) {
            if (e is CancellationException) {
                Log.d("MAPVM_DEBUG", "✅ Запрос отменен (expected)")
                return
            }

            Log.e("MAPVM_DEBUG", "❌ ОШИБКА при загрузке городов: ${e.message}", e)
            _state.update {
                it.copy(
                    isLoading = false,
                    error = "Ошибка загрузки городов: ${e.message ?: "Неизвестная ошибка"}"
                )
            }
            Log.d("MAPVM_DEBUG", "⚠️  Состояние обновлено: isLoading=false, error=${e.message}")
        } finally {
            Log.d("MAPVM_DEBUG", "📍 loadNearbyCities - END")
        }
    }

    private fun calculateRadius(zoom: Float): Int {
        val radius = when {
            zoom <= 3.0f -> 100  // Для очень маленького зума - большой радиус
            zoom <= 5.0f -> 80
            zoom <= 7.0f -> 50
            zoom <= 9.0f -> 20   // Увеличено с 50
            else -> 20
        }
        Log.d("MAPVM_DEBUG", "📐 Рассчитан радиус: $radius км для zoom: $zoom")
        return radius
    }
}