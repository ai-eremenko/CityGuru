package com.example.cityguru.presentation.citydetail

import com.example.cityguru.domain.model.CityDetail

data class CityDetailState(
    val cityDetail: CityDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)