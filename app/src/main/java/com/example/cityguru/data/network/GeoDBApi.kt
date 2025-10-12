package com.example.cityguru.data.network

import com.example.cityguru.data.dto.CityDetailDto
import com.example.cityguru.data.dto.responses.CityResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeoDBApi {
    @GET("v1/geo/cities")
    suspend fun getCities(
        @Query("hateoasMode") hateoasMode: Boolean = false,
        @Query("limit") limit: Int = 5,
        @Query("offset") offset: Int = 0,
        @Query("namePrefix") namePrefix: String? = null
    ): CityResponse

    @GET("v1/geo/cities/{cityId}")
    suspend fun getCityDetail(
        @Path("cityId") cityId: Int
    ): CityDetailDto
}