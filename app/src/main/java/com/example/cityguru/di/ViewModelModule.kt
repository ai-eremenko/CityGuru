package com.example.cityguru.di

import com.example.cityguru.presentation.search.SearchViewModel
import com.example.cityguru.presentation.citydetail.CityDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { CityDetailViewModel(get()) }
}