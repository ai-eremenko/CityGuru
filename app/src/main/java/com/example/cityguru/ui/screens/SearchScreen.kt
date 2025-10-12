package com.example.cityguru.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cityguru.presentation.search.SearchViewModel
import com.example.cityguru.ui.components.CitiesList
import com.example.cityguru.ui.components.ErrorView
import com.example.cityguru.ui.components.LoadingIndicator
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onCityClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search city...") },
            singleLine = true
        )

        when {
            uiState.isLoading -> LoadingIndicator()
            uiState.error != null -> ErrorView(
                message = uiState.error!!,
                onRetry = { viewModel.loadCities(searchQuery.ifEmpty { null }) }
            )
            else -> CitiesList(
                cities = uiState.cities,
                onCityClick = onCityClick
            )
        }
    }
}
