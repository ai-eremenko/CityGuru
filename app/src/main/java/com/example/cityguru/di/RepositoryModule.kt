package com.example.cityguru.di

import com.example.cityguru.data.city.CityRepositoryImpl
import com.example.cityguru.domain.city.CityRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CityRepository> { CityRepositoryImpl(get()) }
}