package com.example.cityguru.domain.city

import com.example.cityguru.domain.model.City
import com.example.cityguru.domain.model.CityDetail

interface CityRepository {
    suspend fun getCities(namePrefix: String?, offset: Int): List<City>
    suspend fun getCityDetail(cityId: Int): CityDetail
    suspend fun getNearbyCities(lat: Double, lng: Double, radius: Int): List<City>
}