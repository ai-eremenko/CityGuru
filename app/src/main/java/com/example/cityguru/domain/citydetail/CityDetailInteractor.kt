package com.example.cityguru.domain.citydetail

import com.example.cityguru.domain.model.CityDetail

interface CityDetailInteractor {
    suspend operator fun invoke(cityId: Int): CityDetail
}