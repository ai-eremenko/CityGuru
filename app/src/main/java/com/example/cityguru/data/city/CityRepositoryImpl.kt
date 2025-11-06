package com.example.cityguru.data.city

import com.example.cityguru.data.network.GeoDBApi
import com.example.cityguru.domain.city.CityRepository
import com.example.cityguru.domain.model.City
import com.example.cityguru.domain.model.CityDetail
import java.io.IOException
import android.util.Log
import java.util.Locale

class CityRepositoryImpl(
    private val api: GeoDBApi
) : CityRepository {

    private companion object {
        const val TAG = "CityRepository"
    }

    override suspend fun getCities(namePrefix: String?, offset: Int): List<City> {
        return try {

            val response = api.getCities(
                namePrefix = namePrefix,
                offset = offset
            )

            response.cities.map { it.toCity() }
        } catch (e: Exception) {
            throw handleApiError(e)
        }
    }

    override suspend fun getCityDetail(cityId: Int): CityDetail {
        return try {
            val response = api.getCityDetail(cityId)
            response.city.toCityDetail()
        } catch (e: Exception) {
            throw handleApiError(e)
        }
    }

    private fun handleApiError(exception: Exception): Exception {
        return when (exception) {
            is IOException -> IOException("Network error: ${exception.message}")
            else -> Exception("API error: ${exception.message}")
        }
    }

    override suspend fun getNearbyCities(
        lat: Double,
        lng: Double,
        radius: Int,
        requestId: String?
    ): List<City> {
        Log.d("MAP_REPOSITORY_DEBUG", "Repository: getNearbyCities called with " +
                "lat=$lat, lng=$lng, radius=$radius, requestId=$requestId")
        return try {
            Log.d("MAP_REPOSITORY_DEBUG", "Making API call...")
            val latForApi = if (lat > 0) {
                "+$lat"
            } else {
                "$lat"
            }
            val lngForApi = if (lng > 0) {
                "+$lng"
            } else {
                "$lng"
            }
            val response = api.getNearbyCities(
                lat = latForApi,
                lng = lngForApi,
                radius = radius,
                cacheBuster = requestId ?: System.currentTimeMillis().toString()
            )
            Log.d("MAP_REPOSITORY_DEBUG", "API response received: ${response.cities.size} cities")
            response.cities.map { it.toCity() }
        } catch (e: Exception) {
            Log.e("MAP_REPOSITORY_DEBUG", "❌ API call FAILED", e)
            emptyList()
        }
    }
}