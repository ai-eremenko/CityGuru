package com.example.cityguru.feauture.search

import com.example.cityguru.domain.model.City

sealed class SearchEvent {
    data class OnCityClicked(val city: City) : SearchEvent()
}