package com.example.cityguru.feauture.citydetail

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cityguru.feauture.citydetail.view.CityDetailView
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    cityId: Int,
    navController: NavController,
    viewModel: CityDetailViewModel = koinViewModel(
        parameters = { parametersOf(cityId)}
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val activity = LocalContext.current as Activity


    CityDetailView(
        state = state,
        onEvent = viewModel::onEvent
    )
    LaunchedEffect(key1 = cityId) {
        viewModel.loadCityDetail(cityId)
    }

    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collectLatest { effect ->
            handleSideEffect(
                sideEffect = effect, activity = activity, navController = navController
            )
        }
    }
}

private fun handleSideEffect(
    activity: Activity,
    sideEffect: CityDetailSideEffect,
    navController: NavController
) {
    when (sideEffect) {
        CityDetailSideEffect.Finish -> navController.popBackStack()
        is CityDetailSideEffect.OnWikidataButtonClicked -> {
            try {
                val wikiDataUrl = "https://www.wikidata.org/wiki/${sideEffect.wikiDataId}"
                val intent = android.content.Intent(
                    android.content.Intent.ACTION_VIEW,
                    wikiDataUrl.toUri()
                )
                activity.startActivity(intent)
            } catch (e: Exception) {
                Toast
                    .makeText(activity, e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}