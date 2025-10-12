package com.example.cityguru.domain.model

data class CityDetail(
    val id: Int,
    val name: String,
    val country: String,
    val countryCode: String,
    val elevationMeters: Int,
    val population: Int,
    val wikiDataId: String
)