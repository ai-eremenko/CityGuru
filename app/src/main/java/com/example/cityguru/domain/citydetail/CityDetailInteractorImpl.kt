package com.example.cityguru.domain.citydetail

import com.example.cityguru.domain.city.CityRepository
import com.example.cityguru.domain.model.CityDetail

class CityDetailInteractorImpl(
    private val repository: CityRepository
) : CityDetailInteractor {
    override suspend operator fun invoke(cityId: Int): CityDetail {
        if (cityId <= 0) {
            throw IllegalArgumentException("Invalid city ID")
        }

        return repository.getCityDetail(cityId)
    }
}