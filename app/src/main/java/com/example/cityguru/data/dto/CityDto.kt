package com.example.cityguru.data.dto

import com.example.cityguru.domain.model.City
import com.yandex.mapkit.geometry.Point

data class CityDto(
    val id: Int,
    val name: String,
    val country: String,
    val countryCode: String,
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun toCity(): City = City(
        id = this.id,
        name = this.name,
        country = this.country,
        countryCode = this.countryCode,
        point = Point(latitude ?: 0.0, longitude ?: 0.0)
    )
}