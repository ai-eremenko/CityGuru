package com.example.cityguru.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkHttpClient(androidApplication()) }
    single { provideRetrofit(get()) }
    single { provideGeoDBApi(get()) }
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

fun provideGeoDBApi(retrofit: Retrofit): com.example.cityguru.data.network.GeoDBApi {
    return retrofit.create(com.example.cityguru.data.network.GeoDBApi::class.java)
}