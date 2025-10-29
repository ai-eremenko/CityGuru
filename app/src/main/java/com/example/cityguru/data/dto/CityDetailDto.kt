package com.example.cityguru.data.dto

import com.example.cityguru.domain.model.City
import com.example.cityguru.domain.model.CityDetail
import com.yandex.mapkit.geometry.Point

data class CityDetailDto(
    val id: Int,
    val name: String,
    val country: String,
    val countryCode: String,
    val elevationMeters: Int,
    val population: Int,
    val wikiDataId: String,
    val latitude: Double,
    val longitude: Double,
) {
    fun toCityDetail(): CityDetail = CityDetail(
        id = this.id,
        name = this.name,
        country = this.country,
        countryCode = this.countryCode,
        elevationMeters = this.elevationMeters,
        population = this.population,
        wikiDataId = this.wikiDataId
    )

    fun toCity(): City = City(
        id = this.id,
        name = this.name,
        country = this.country,
        countryCode = this.countryCode,
        point = Point(this.latitude, this.longitude)
    )
}