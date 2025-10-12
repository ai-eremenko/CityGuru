package com.example.cityguru.data.dto

import com.example.cityguru.domain.model.City

data class CityDto(
    val id: Int,
    val name: String,
    val country: String,
    val countryCode: String
) {
    fun toCity(): City = City(id, name, country, countryCode)
}