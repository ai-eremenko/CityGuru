package com.example.cityguru.ui.screens

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cityguru.domain.model.City
import com.example.cityguru.presentation.citydetail.CityDetailViewModel
import com.example.cityguru.ui.components.CityDetailBottomSheetContent
import com.example.cityguru.ui.components.YandexMapComponent
import com.yandex.mapkit.mapview.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onMapViewCreated: (MapView) -> Unit,
    savedInstanceState: Bundle? = null
) {
    var selectedCity by remember { mutableStateOf<City?>(null) }
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val cityDetailViewModel: CityDetailViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        YandexMapComponent(
            onMapViewCreated = onMapViewCreated,
            savedInstanceState = savedInstanceState,
            modifier = Modifier.fillMaxSize(),
            onCitySelected = { city ->
                selectedCity = city
                cityDetailViewModel.loadCityDetail(city.id)
            }
        )

        if (selectedCity != null) {
            ModalBottomSheet(
                onDismissRequest = { selectedCity = null },
                sheetState = sheetState
            ) {
                CityDetailBottomSheetContent(
                    city = selectedCity,
                    viewModel = cityDetailViewModel,
                    onDismiss = { selectedCity = null }
                )
            }
        }
    }
}