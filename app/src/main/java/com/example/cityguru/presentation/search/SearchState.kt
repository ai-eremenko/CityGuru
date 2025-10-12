package com.example.cityguru.presentation.search

import com.example.cityguru.domain.model.City

data class SearchState(
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)