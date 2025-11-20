package com.example.cityguru.feauture.search

import com.example.cityguru.domain.model.City

data class SearchState(
    val listCities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
)