package com.example.cityguru.feauture.map

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.cityguru.domain.model.City
import com.example.cityguru.feauture.citydetail.CityDetailViewModel
import com.example.cityguru.feauture.map.view.CityDetailBottomSheetContent
import com.example.cityguru.feauture.map.view.YandexMapComponent
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@SuppressLint("ContextCastToActivity", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onMapViewCreated: (MapView) -> Unit,
    navController: NavController,
    viewModel: MapViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val activity = LocalContext.current as Activity

    var selectedCity by remember { mutableStateOf<City?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val cityDetailViewModel: CityDetailViewModel = koinViewModel()

        YandexMapComponent(
            onMapRegionChanged = { center, zoom ->
                viewModel.onMapRegionChanged(center, zoom)
            },
            onMapViewCreated = onMapViewCreated,
            modifier = Modifier.fillMaxSize(),
            cities = state.cities,
            onEvent = viewModel::onEvent
        )

        if (showBottomSheet && selectedCity != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    selectedCity = null },
                sheetState = sheetState
            ) {
                CityDetailBottomSheetContent(
                    cityId = selectedCity!!.id,
                    viewModel = cityDetailViewModel,
                    onEvent = {}
                )
            }
        }

    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is MapSideEffect.OnCityFlagClicked -> {
                    selectedCity = effect.city
                    showBottomSheet = true
                }
            }
        }
    }
}