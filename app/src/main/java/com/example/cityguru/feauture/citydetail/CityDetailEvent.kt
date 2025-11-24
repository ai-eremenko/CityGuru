package com.example.cityguru.feauture.citydetail

sealed class CityDetailEvent {
    data object OnBackButtonClicked : CityDetailEvent()
    data object OnWikidataButtonClicked : CityDetailEvent()
}