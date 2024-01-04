package com.nyangzzi.dayFlower.presentation.feature.flowerDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyangzzi.dayFlower.domain.usecase.GetFlowerDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowerDetailViewModel @Inject constructor(
    val getFlowerDetailUseCase: GetFlowerDetailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(FlowerDetailUiState())
    val uiState: StateFlow<FlowerDetailUiState> = _uiState

    fun onEvent(event: FlowerDetailOnEvent) {
        viewModelScope.launch {
            when (event) {
                is FlowerDetailOnEvent.OnSearchDetail -> getDetailFlower(event.dataNo)
                FlowerDetailOnEvent.OnDismiss -> {
                    _uiState.update { it.copy(flowerDetail = ResultWrapper.Loading) }
                }
            }
        }
    }

    private suspend fun getDetailFlower(dataNo: Int?) {
        if (dataNo == null) {
            _uiState.update {
                it.copy(flowerDetail = ResultWrapper.Error("오류가 발생했습니다. 다시 시도해주세요."))
            }
        } else {
            getFlowerDetailUseCase(
                requestFlowerDetail = RequestFlowerDetail(
                    dataNo = dataNo
                )
            ).collect { result ->
                _uiState.update {
                    it.copy(flowerDetail = result)
                }
            }
        }
    }
}