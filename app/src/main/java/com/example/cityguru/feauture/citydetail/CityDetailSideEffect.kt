package com.example.cityguru.feauture.citydetail

sealed class CityDetailSideEffect {
    data object Finish : CityDetailSideEffect()
    data class OnWikidataButtonClicked(val wikiDataId: String) : CityDetailSideEffect()
}