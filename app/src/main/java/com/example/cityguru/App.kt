package com.example.cityguru


import android.app.Application
import com.example.cityguru.di.interactorModule
import com.example.cityguru.di.networkModule
import com.example.cityguru.di.repositoryModule
import com.example.cityguru.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}