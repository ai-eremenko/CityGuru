package com.example.cityguru.feauture.map

import com.example.cityguru.domain.model.City

sealed class MapEvent {
    data class OnCityFlagClicked(val city: City) : MapEvent()
}