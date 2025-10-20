package com.example.cityguru.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguru.domain.city.CityInteractor
import com.example.cityguru.domain.model.City
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val cityInteractor: CityInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var currentOffset = 0
    private var canLoadMore = true
    private val initialPageSize = 3

    init {
        loadCities()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.length >= 2) {
            viewModelScope.launch {
                debounceSearch(query)
            }
        } else if (query.isEmpty()) {
            loadCities(reset = true)
        }
    }

    private var searchJob: Job? = null
    private fun debounceSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            loadCities(query, reset = true)
        }
    }


    fun loadCities(namePrefix: String? = null, reset: Boolean = false) {
        if (reset) {
            currentOffset = 0
            canLoadMore = true
            _uiState.update { it.copy(cities = emptyList()) }
        }

        if (!canLoadMore && !reset) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val allCities = mutableListOf<City>()

                for (i in 0 until initialPageSize) {
                    val cities = cityInteractor(namePrefix, currentOffset + allCities.size)
                    allCities.addAll(cities)

                    if (cities.size < 5) {
                        canLoadMore = false
                        break
                    }
                }

                _uiState.update {
                    it.copy(
                        cities = if (reset) allCities else it.cities + allCities,
                        isLoading = false,
                        error = null
                    )
                }

                currentOffset += allCities.size
                canLoadMore = allCities.size == initialPageSize * 5

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}