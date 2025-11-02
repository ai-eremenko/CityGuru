package com.example.cityguru.presentation.map

import com.example.cityguru.domain.model.City

data class MapState(
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)