package com.example.cityguru.feauture.search

import com.example.cityguru.domain.model.City

sealed class SearchSideEffect {
    data class NavigateToCityDetail(val city: City) : SearchSideEffect()
}