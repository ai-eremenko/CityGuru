package com.example.cityguru.di

import com.example.cityguru.data.city.CityRepositoryImpl
import com.example.cityguru.data.network.GeoDBApi
import com.example.cityguru.domain.city.CityInteractor
import com.example.cityguru.domain.city.CityRepository
import com.example.cityguru.domain.citydetail.CityDetailInteractor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// di/AppModule.kt
val appModule = module {

    // Network
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideGeoDBApi(get()) }

    // Repository
    single<CityRepository> { CityRepositoryImpl(get()) }

    // Interactors (Use Cases)
    single { CityInteractor(get()) }
    single { CityDetailInteractor(get()) }

    // ViewModels
    viewModel { CityViewModel(get()) }
    viewModel { CityDetailViewModel(get()) }
}

// Network providers
fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://geodb-free-service.wirefreethought.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideGeoDBApi(retrofit: Retrofit): GeoDBApi {
    return retrofit.create(GeoDBApi::class.java)
}