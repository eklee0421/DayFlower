package com.nyangzzi.dayFlower.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerDay.RequestFlowerDay
import com.nyangzzi.dayFlower.domain.usecase.datastore.RecentViewFlowerUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.CheckIsSavedUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.FirebaseManager
import com.nyangzzi.dayFlower.domain.usecase.firebase.GetUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.UpdateLockerUseCase
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
    private val firebaseManager: FirebaseManager,
    private val updateLockerUseCase: UpdateLockerUseCase,
    private val recentViewFlowerUseCase: RecentViewFlowerUseCase
) : ViewModel() {

    private val _user = getUserUseCase()
    private val _savedFlower = firebaseManager.locker()
    private val _recentViewFlower = recentViewFlowerUseCase.recentViewFlower

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> =
        combine(
            _uiState,
            _user,
            _savedFlower,
            _recentViewFlower
        ) { state, user, savedFlower, recentViewFlower ->
            state.copy(
                user = user,
                savedFlower = savedFlower,
                recentViewFlower = recentViewFlower
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
                is HomeEvent.SetShowDetail -> _uiState.update {
                    it.copy(
                        isShowDetail = event.isShown,
                        isShowDataNo = event.dataNo
                    )
                }

                is HomeEvent.UpdateLocker -> updateFlower(
                    isSaved = event.isSaved,
                    flower = event.flowerDetail
                )
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


    private suspend fun updateFlower(isSaved: Boolean, flower: FlowerDetail) {
        updateLockerUseCase(
            isSaved = isSaved,
            flower = flower
        ).collect { result ->
            when (result) {
                is ResultWrapper.Success -> {

                }

                else -> {}
            }

        }
    }
}