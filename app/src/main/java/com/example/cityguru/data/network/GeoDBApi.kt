package com.example.cityguru.data.network

import com.example.cityguru.data.dto.responses.CityDetailResponse
import com.example.cityguru.data.dto.responses.CityResponse
import com.example.cityguru.data.dto.responses.NearbyCitiesResponse
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
    ): CityDetailResponse

    @GET("v1/geo/locations/{lat}-{lng}/nearbyCities")
    suspend fun getNearbyCities(
        @Path("lat") lat: Double,
        @Path("lng") lng: Double,
        @Query("radius") radius: Int = 100,
        @Query("limit") limit: Int = 10
    ): NearbyCitiesResponse
}