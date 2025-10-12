package com.example.cityguru.domain.city

import com.example.cityguru.domain.model.City

class CityInteractorImpl (
    private val repository: CityRepository
): CityInteractor {
    override suspend operator fun invoke(namePrefix: String?, offset: Int): List<City> {
        if (namePrefix != null) {
            if (namePrefix.length < 2) {
                return emptyList() // Минимум 2 символа для поиска
            }
            if (namePrefix.length > 20) {
                throw IllegalArgumentException("Search query too long")
            }
        }

        if (offset < 0) {
            throw IllegalArgumentException("Offset cannot be negative")
        }

        return repository.getCities(namePrefix, offset)
    }
}