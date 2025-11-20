package com.example.cityguru.data

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.cityguru.data.city.CityRepositoryImpl
import com.example.cityguru.data.network.GeoDBApi
import com.example.cityguru.domain.city.CityRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single { provideOkHttpClient(androidApplication()) }
    single { provideRetrofit(get()) }
    single { provideGeoDBApi(get()) }
    single<CityRepository> { CityRepositoryImpl(get()) }
}

fun provideOkHttpClient(application: Application): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(ChuckerInterceptor(application))
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