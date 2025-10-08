package com.example.cityguru.domain.model

data class CitiesResponse(
    val data: List<City>,
    val metadata: Metadata
)

data class City(
    val id: Int,
    val city: String,
    val country: String,
    val countryCode: String,
    val elevationMeters: Int?,
    val population: Int?,
    val wikiDataId: String?
)

data class CityDetail(
    val data: City
)

data class Metadata(
    val currentOffset: Int,
    val totalCount: Int
)