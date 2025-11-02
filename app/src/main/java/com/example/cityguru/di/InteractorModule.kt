package com.example.cityguru.di

import com.example.cityguru.domain.city.CityInteractor
import com.example.cityguru.domain.city.CityInteractorImpl
import com.example.cityguru.domain.citydetail.CityDetailInteractor
import com.example.cityguru.domain.citydetail.CityDetailInteractorImpl
import com.example.cityguru.domain.map.MapInteractor
import com.example.cityguru.domain.map.MapInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<CityInteractor> { CityInteractorImpl(get()) }
    single<MapInteractor> { MapInteractorImpl(get()) }
    single<CityDetailInteractor> { CityDetailInteractorImpl(get()) }
}