package com.example.cityguru.feauture.citydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguru.domain.citydetail.CityDetailInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CityDetailViewModel(
    private val cityDetailInteractor: CityDetailInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(CityDetailState())
    val state: StateFlow<CityDetailState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CityDetailSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: CityDetailEvent) {
        when (event) {
            CityDetailEvent.OnBackButtonClicked -> onBackButtonClicked()
            CityDetailEvent.OnWikidataButtonClicked -> onWikidataButtonClicked()
        }
    }


    fun loadCityDetail(cityId: Int) {
        viewModelScope.launch {
            _state.update { it.copy() }
            try {
                val cityDetail = cityDetailInteractor(cityId)
                _state.update {
                    it.copy(
                        cityDetail = cityDetail
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy()
                }
            }
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _sideEffect.emit(CityDetailSideEffect.Finish)
        }
    }

    private fun onWikidataButtonClicked(){
        viewModelScope.launch {
            val wikiDataId = _state.value.cityDetail?.wikiDataId ?: return@launch
            if (wikiDataId.isBlank()) return@launch
            _sideEffect.emit(CityDetailSideEffect.OnWikidataButtonClicked(wikiDataId))
        }
    }

}