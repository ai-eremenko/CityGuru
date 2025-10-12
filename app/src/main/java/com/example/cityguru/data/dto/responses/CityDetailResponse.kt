package com.example.cityguru.data.dto.responses

import com.example.cityguru.data.dto.CityDetailDto
import com.google.gson.annotations.SerializedName

data class CityDetailResponse(
    @SerializedName("data")
    val city: CityDetailDto
)