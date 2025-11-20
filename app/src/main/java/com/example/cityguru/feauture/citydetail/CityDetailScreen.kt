package com.example.cityguru.feauture.citydetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.cityguru.feauture.citydetail.view.CityDetailView
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    cityId: Int,
    viewModel: CityDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(cityId) {
        viewModel.loadCityDetail(cityId)
    }

    CityDetailView(
        state = state,
        onBackClick = onBackClick,
        onRetry = { viewModel.loadCityDetail(cityId) }
    )
}