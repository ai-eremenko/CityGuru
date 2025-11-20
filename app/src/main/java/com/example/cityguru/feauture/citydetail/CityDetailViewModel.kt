package com.example.cityguru.feauture.citydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguru.domain.citydetail.CityDetailInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CityDetailViewModel(
    private val cityDetailInteractor: CityDetailInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(CityDetailState())
    val state: StateFlow<CityDetailState> = _state.asStateFlow()

    fun loadCityDetail(cityId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val cityDetail = cityDetailInteractor(cityId)
                _state.update {
                    it.copy(
                        cityDetail = cityDetail,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}