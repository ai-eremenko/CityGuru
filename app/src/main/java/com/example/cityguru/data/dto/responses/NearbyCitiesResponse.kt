package com.example.cityguru.data.dto.responses

import com.example.cityguru.data.dto.CityDto
import com.google.gson.annotations.SerializedName

data class NearbyCitiesResponse(
    @SerializedName("data")
    val cities: List<CityDto>
)