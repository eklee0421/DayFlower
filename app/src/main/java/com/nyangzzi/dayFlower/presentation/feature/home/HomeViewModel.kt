package com.nyangzzi.dayFlower.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyangzzi.dayFlower.domain.usecase.GetFlowerDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val flowerDetailUseCase: GetFlowerDetailUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            getFlowerDetail()
        }
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetFlowerDetail -> getFlowerDetail()
                is HomeEvent.SetShowDetail -> _uiState.update { it.copy(isShowDetail = event.isShown) }
            }
        }
    }

    private suspend fun getFlowerDetail() {
        flowerDetailUseCase(
            requestFlowerDetail = RequestFlowerDetail(
                fMonth = _uiState.value.localDate.month.value,
                fDay = _uiState.value.localDate.dayOfMonth
            )
        ).collect { result ->
            _uiState.update {
                it.copy(flowerDetail = result)
            }
        }
    }

}