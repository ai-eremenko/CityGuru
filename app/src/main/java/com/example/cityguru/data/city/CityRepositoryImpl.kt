package com.example.cityguru.data.city

import com.example.cityguru.data.network.GeoDBApi
import com.example.cityguru.domain.city.CityRepository
import com.example.cityguru.domain.model.City
import com.example.cityguru.domain.model.CityDetail
import java.io.IOException
import android.util.Log

class CityRepositoryImpl(
    private val api: GeoDBApi
) : CityRepository {

    private companion object {
        const val TAG = "CityRepository"
    }

    override suspend fun getCities(namePrefix: String?, offset: Int): List<City> {
        return try {
            Log.d(TAG, "getCities called: namePrefix='$namePrefix', offset=$offset")

            val response = api.getCities(
                namePrefix = namePrefix,
                offset = offset
            )

            Log.d(TAG, "API response received: ${response.cities.size} cities")
            Log.d(TAG, "Response details: $response")

            response.cities.map { it.toCity() }
        } catch (e: Exception) {
            Log.e(TAG, "Error in getCities: ${e.message}", e)
            throw handleApiError(e)
        }
    }

    override suspend fun getCityDetail(cityId: Int): CityDetail {
        return try {
            Log.d(TAG, "getCityDetail called: cityId=$cityId")

            val response = api.getCityDetail(cityId)
            Log.d(TAG, "City detail response: $response")

            response.city.toCityDetail()
        } catch (e: Exception) {
            Log.e(TAG, "Error in getCityDetail: ${e.message}", e)
            throw handleApiError(e)
        }
    }

    private fun handleApiError(exception: Exception): Exception {
        Log.e(TAG, "API Error handled: ${exception.javaClass.simpleName} - ${exception.message}")
        return when (exception) {
            is IOException -> IOException("Network error: ${exception.message}")
            else -> Exception("API error: ${exception.message}")
        }
    }
}