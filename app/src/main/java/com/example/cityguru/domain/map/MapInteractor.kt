package com.example.cityguru.domain.map

import com.example.cityguru.domain.model.City

interface MapInteractor {
    suspend fun getNearbyCities(lat: Double, lng: Double, radius: Int): List<City>
}