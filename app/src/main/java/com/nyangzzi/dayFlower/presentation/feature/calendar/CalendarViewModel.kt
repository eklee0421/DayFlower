package com.nyangzzi.dayFlower.presentation.feature.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyangzzi.dayFlower.domain.usecase.network.GetFlowerMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val flowerMonthUseCase: GetFlowerMonthUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState

    init {
        viewModelScope.launch {
            getFlowerMonth()
        }
    }

    fun onEvent(event: CalendarOnEvent) {
        viewModelScope.launch {
            when (event) {
                CalendarOnEvent.OnNextMonth -> {
                    _uiState.update { it.copy(localDate = _uiState.value.localDate.plusMonths(1)) }
                    getFlowerMonth()
                }

                CalendarOnEvent.OnPrevMonth -> {
                    _uiState.update { it.copy(localDate = _uiState.value.localDate.minusMonths(1)) }
                    getFlowerMonth()
                }

                CalendarOnEvent.OnSearchMonth -> {
                    getFlowerMonth()
                }

                is CalendarOnEvent.SetDetailDialog -> {
                    _uiState.update { it.copy(isDetail = event.isShown) }
                }
            }
        }
    }

    private suspend fun getFlowerMonth() {
        flowerMonthUseCase(
            requestFlowerMonth = RequestFlowerMonth(
                fMonth = _uiState.value.localDate.month.value
            )
        ).collect { result ->
            _uiState.update {
                it.copy(flowerMonth = result)
            }
        }
    }
}