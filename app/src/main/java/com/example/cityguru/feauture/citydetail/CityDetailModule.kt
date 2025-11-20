package com.example.cityguru.feauture.citydetail

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cityDetailModule = module {
    viewModel { CityDetailViewModel(get()) }
}