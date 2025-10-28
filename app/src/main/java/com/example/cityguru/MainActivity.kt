package com.example.cityguru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.cityguru.ui.navigation.AppNavigation
import com.example.cityguru.ui.screens.MainScreen
import com.example.cityguru.ui.theme.CityGuruTheme
import com.example.cityguru.utils.RequestLocationPermission
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MainActivity : ComponentActivity() {
    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)

        setContent {
            CityGuruTheme {

                RequestLocationPermission(
                    onPermissionGranted = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        onMapViewCreated = { mapView ->
                            this.mapView = mapView
                        }
                    )
                }
            },
                    onPermissionDenied = {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            MainScreen(
                                onMapViewCreated = { mapView ->
                                    this.mapView = mapView
                                }
                            )
                        }
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
    }
}