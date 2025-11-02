package com.example.cityguru.domain.map

import com.example.cityguru.domain.city.CityRepository
import com.example.cityguru.domain.model.City

class MapInteractorImpl(
    private val cityRepository: CityRepository
) : MapInteractor {

    override suspend fun getNearbyCities(lat: Double, lng: Double, radius: Int): List<City> {
        return cityRepository.getNearbyCities(lat, lng, radius)
    }
}