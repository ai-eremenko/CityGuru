package com.example.cityguru.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cityguru.presentation.search.SearchViewModel
import com.example.cityguru.ui.view.SearchView
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onCityClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    SearchView(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChanged = { viewModel.onSearchQueryChange(it) },
        onCityClick = onCityClick,
        loadCities = { viewModel.loadCities(searchQuery.ifEmpty { null }) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {

}