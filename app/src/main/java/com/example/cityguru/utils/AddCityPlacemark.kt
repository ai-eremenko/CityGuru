package com.example.cityguru.utils

import com.example.cityguru.R
import com.example.cityguru.domain.model.City
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

private fun MapView.addCityPlacemark(city: City, onCitySelected: (City) -> Unit) {
    val placemark = map.mapObjects.addPlacemark(city.point)

    placemark.setIcon(
        ImageProvider.fromResource(context, R.drawable.ic_flag_marker)
    )

    placemark.setText(city.name)
    placemark.isDraggable = false

    placemark.addTapListener { mapObject, point ->

        onCitySelected(city)
        true
    }

    placemark.userData = city
}

fun MapView.addCitiesFromApiData(
    apiData: List<City>,
    onCitySelected: (City) -> Unit
) {
    apiData.forEach { city ->
        addCityPlacemark(city, onCitySelected)
    }
}