package com.example.cityguru

import android.app.Application
import com.example.cityguru.data.dataModule
import com.example.cityguru.domain.interactorModule
import com.example.cityguru.feauture.citydetail.cityDetailModule
import com.example.cityguru.feauture.map.mapModule
import com.example.cityguru.feauture.search.searchModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("1bf8dcc8-71a7-4887-b314-c57700b84c4b")

        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                interactorModule,
                cityDetailModule,
                mapModule,
                searchModule
            )
        }
    }
}