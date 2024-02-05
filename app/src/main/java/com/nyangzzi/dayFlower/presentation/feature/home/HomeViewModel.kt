package com.nyangzzi.dayFlower.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.domain.model.flowerDay.RequestFlowerDay
import com.nyangzzi.dayFlower.domain.usecase.firebase.CheckIsSavedUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.FirebaseManager
import com.nyangzzi.dayFlower.domain.usecase.firebase.GetUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.network.GetFlowerDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dayFlowerUseCase: GetFlowerDayUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val checkIsSavedUseCase: CheckIsSavedUseCase,
    private val firebaseManager: FirebaseManager
) : ViewModel() {

    private val _user = getUserUseCase()
    private val _savedFlower = firebaseManager.locker()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> =
        combine(_uiState, _user, _savedFlower) { state, user, savedFlower ->
            state.copy(
                user = user,
                savedFlower = savedFlower
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(1000),
            HomeUiState()
        )

    init {
        viewModelScope.launch {
            getDayFlower()
        }
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetDayFlower -> getDayFlower()
                is HomeEvent.SetShowDetail -> _uiState.update { it.copy(isShowDetail = event.isShown) }
            }
        }
    }

    private suspend fun getDayFlower() {
        dayFlowerUseCase(
            requestFlowerDay = RequestFlowerDay(
                fMonth = _uiState.value.localDate.month.value,
                fDay = _uiState.value.localDate.dayOfMonth
            )
        ).collect { result ->
            _uiState.update {
                it.copy(flowerDetail = result)
            }
        }
    }

    private suspend fun checkIsSaved(dataNo: Int) {
        checkIsSavedUseCase(dataNo).collect {

        }
    }
}