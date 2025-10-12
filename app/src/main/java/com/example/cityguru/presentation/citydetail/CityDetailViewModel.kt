package com.example.cityguru.presentation.citydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguru.domain.citydetail.CityDetailInteractor
import com.example.cityguru.domain.model.CityDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// presentation/viewmodel/CityDetailViewModel.kt
class CityDetailViewModel(
    private val cityDetailInteractor: CityDetailInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(CityDetailState())
    val uiState: StateFlow<CityDetailState> = _uiState.asStateFlow()

    fun loadCityDetail(cityId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val cityDetail = cityDetailInteractor(cityId)
                _uiState.update {
                    it.copy(
                        cityDetail = cityDetail,
                        isLoading = false,
                        error = null
                    )
                }
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

    fun retry(cityId: Int) {
        loadCityDetail(cityId)
    }
}