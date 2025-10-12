package com.example.cityguru.domain.city

import com.example.cityguru.domain.model.City

interface CityInteractor {
    suspend operator fun invoke(namePrefix: String?, offset: Int): List<City>
}