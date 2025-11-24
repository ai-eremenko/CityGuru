package com.example.cityguru.feauture.map.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.cityguru.feauture.citydetail.CityDetailEvent
import com.example.cityguru.feauture.citydetail.CityDetailViewModel
import com.example.cityguru.uikit.components.CityDetailContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun CityDetailBottomSheetContent(
    cityId: Int,
    viewModel: CityDetailViewModel = koinViewModel(),
    onEvent: (CityDetailEvent) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(cityId) {
         viewModel.loadCityDetail(cityId)
    }

    state.cityDetail?.let { cityDetail ->
        CityDetailContent(
            cityDetail = cityDetail,
            onEvent = onEvent
        )
    }
}