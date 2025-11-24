package com.example.cityguru.feauture.map

import com.example.cityguru.domain.model.City

sealed class MapSideEffect {
    data class OnCityFlagClicked(val city: City) : MapSideEffect()
}