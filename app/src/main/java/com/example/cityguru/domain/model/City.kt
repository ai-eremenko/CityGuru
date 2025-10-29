package com.example.cityguru.domain.model

import com.yandex.mapkit.geometry.Point

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val countryCode: String,
    val point: Point
)