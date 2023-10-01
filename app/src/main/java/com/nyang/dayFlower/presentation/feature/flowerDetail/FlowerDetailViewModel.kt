package com.nyang.dayFlower.presentation.feature.flowerDetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyang.dayFlower.domain.usecase.GetFlowerDetailUseCase
import com.nyang.dayFlower.domain.usecase.GetFlowerMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FlowerDetailViewModel @Inject constructor(
    private val flowerDetailUseCase: GetFlowerDetailUseCase,
    private val flowerMonthUseCase: GetFlowerMonthUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlowerDetailUiState())
    val uiState: StateFlow<FlowerDetailUiState> = _uiState

    init{
        viewModelScope.launch {
            getFlowerDetail()
        }
    }

    fun onEvent(event: FlowerDetailEvent){
        viewModelScope.launch {
            when (event) {
                is FlowerDetailEvent.SearchFlower -> {
                    setLocalDate(LocalDate.of(LocalDate.now().year, event.month, event.day))
                    getFlowerDetail()
                }
                is FlowerDetailEvent.SearchNextFlower -> {
                    setLocalDate(_uiState.value.localDate.plusDays(1))
                    getFlowerDetail()
                }
                is FlowerDetailEvent.SearchPrevFlower -> {
                    setLocalDate(_uiState.value.localDate.minusDays(1))
                    getFlowerDetail()
                }
                is FlowerDetailEvent.ShowDatePicker ->{
                    _uiState.update { it.copy(isDatePicker = true) }
                }
                is FlowerDetailEvent.IsChangeView -> {
                    _uiState.update {
                        it.copy(isCalendar = event.isCalendar)
                    }
                    setLocalDate(LocalDate.of(_uiState.value.localDate.year, event.month ?: _uiState.value.localDate.month.value , event.day ?: _uiState.value.localDate.dayOfMonth))
                    if(event.isCalendar) getFlowerMonth()
                    else getFlowerDetail()
                }
            }
        }
    }

    private fun setLocalDate(localDate : LocalDate){
        _uiState.update {
            it.copy(localDate = localDate, isDatePicker = false)
        }
    }

    private suspend fun getFlowerDetail() {
        flowerDetailUseCase(
            requestFlowerDetail = RequestFlowerDetail(
                fMonth = _uiState.value.localDate.month.value,
                fDay = _uiState.value.localDate.dayOfMonth)).collect { result->
            _uiState.update {
                it.copy(flowerDetail = result)
            }
        }
    }

    private suspend fun getFlowerMonth() {
        flowerMonthUseCase(
            requestFlowerMonth = RequestFlowerMonth(
                fMonth = _uiState.value.localDate.month.value)
        ).collect { result->
            _uiState.update {
                it.copy(flowerMonth = result)
            }
        }
    }

}