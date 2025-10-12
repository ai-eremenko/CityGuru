package com.example.cityguru.data.dto

import com.example.cityguru.domain.model.CityDetail

data class CityDetailDto(
    val id: Int,
    val name: String,
    val country: String,
    val countryCode: String,
    val elevationMeters: Int,
    val population: Int,
    val wikiDataId: String
) {
    fun toCityDetail(): CityDetail = CityDetail(
        id, name, country, countryCode, elevationMeters, population, wikiDataId
    )
}