package com.example.cityguru.utils

import android.content.Context
import com.example.cityguru.domain.model.City
import com.example.cityguru.presentation.map.CityMarkerCreator
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider

fun MapObjectCollection.addCitiesFromApiData(
    cities: List<City>,
    onCitySelected: (City) -> Unit,
    context: Context
): List<PlacemarkMapObject> {
    val markerCreator = CityMarkerCreator(context)
    val placemarks = mutableListOf<PlacemarkMapObject>()

    cities.forEach { city ->
        val markerBitmap = markerCreator.createCityMarker(city)
        val placemark = this.addPlacemark(
            city.point,
            ImageProvider.fromBitmap(markerBitmap)
        )

        placemark.userData = city
        placemark.isVisible = true

        placemark.addTapListener { mapObject, point ->
            val selectedCity = mapObject.userData as? City
            selectedCity?.let { onCitySelected(it) }
            true
        }

        placemarks.add(placemark)
    }

    return placemarks
}