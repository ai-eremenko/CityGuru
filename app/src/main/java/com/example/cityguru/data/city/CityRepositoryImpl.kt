package com.example.cityguru.data.city

import com.example.cityguru.domain.city.CityRepository
import com.example.cityguru.domain.model.City
import com.example.cityguru.domain.model.CityDetail
import java.io.IOException

// data/repository/CityRepositoryImpl.kt
class CityRepositoryImpl(
    private val api: GeoDBApi
) : CityRepository {

    override suspend fun getCities(namePrefix: String?, offset: Int): List<City> {
        return try {
            val response = api.getCities(
                namePrefix = namePrefix,
                offset = offset
            )
            response.data.map { it.toCity() }
        } catch (e: Exception) {
            throw handleApiError(e)
        }
    }

    override suspend fun getCityDetail(cityId: Int): CityDetail {
        return try {
            val response = api.getCityDetail(cityId)
            response.data.toCityDetail()
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
}
