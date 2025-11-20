package com.example.cityguru.feauture.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cityguru.feauture.search.view.SearchView
import com.example.cityguru.uikit.navigation.Screen
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    SearchView(
        state = state,
        searchQuery = searchQuery,
        onSearchQueryChanged = { viewModel.onSearchQueryChange(it) },
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            handleSideEffect(sideEffect = sideEffect, navController = navController)
        }
    }
}

private fun handleSideEffect(sideEffect: SearchSideEffect, navController: NavController) {
    when (sideEffect) {
        is SearchSideEffect.NavigateToCityDetail ->
            navController.navigate(Screen.CityDetail.createRoute(sideEffect.city.id))
    }
}
