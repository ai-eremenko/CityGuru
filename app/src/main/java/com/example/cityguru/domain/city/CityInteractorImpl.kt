package com.example.cityguru.domain.city

import com.example.cityguru.domain.model.City

class CityInteractorImpl (
    private val repository: CityRepository
): CityInteractor {
    override suspend operator fun invoke(namePrefix: String?, offset: Int): List<City> {
        if (namePrefix != null && namePrefix.length < 2) {
            return emptyList()
        }
        return repository.getCities(namePrefix, offset)
    }
}