package com.example.cityguru.domain.map

import android.util.Log
import com.example.cityguru.domain.city.CityRepository
import com.example.cityguru.domain.model.City

class MapInteractorImpl(
    private val cityRepository: CityRepository
) : MapInteractor {

    override suspend fun getNearbyCities(
        lat: Double,
        lng: Double,
        radius: Int,
        requestId: String?
    ): List<City> {
        Log.d("MAP_INTERACTOR_DEBUG", "Calling repository with " +
                "lat=$lat, lng=$lng, radius=$radius\"")

        val result = cityRepository.getNearbyCities(lat, lng, radius, requestId)
        Log.d("MAP_INTERACTOR_DEBUG","✅ INTERACTOR: Repository returned ${result.size} cities")
        result.forEach { city ->
            Log.d("MAP_INTERACTOR_DEBUG","   🏙️  City: ${city.name} (${city.point.latitude}, ${city.point.longitude})")
        }
        return result
    }
}