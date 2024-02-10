package com.nyangzzi.dayFlower.presentation.feature.locker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.usecase.firebase.FirebaseManager
import com.nyangzzi.dayFlower.domain.usecase.firebase.GetUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.UpdateLockerUseCase
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
class LockerViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val firebaseManager: FirebaseManager,
    private val updateLockerUseCase: UpdateLockerUseCase,
) : ViewModel() {

    private val _user = getUserUseCase()
    private val _savedFlower = firebaseManager.locker()

    private val _uiState = MutableStateFlow(LockerUiState())
    val uiState: StateFlow<LockerUiState> =
        combine(_uiState, _user, _savedFlower) { state, user, savedFlower ->
            state.copy(
                user = user,
                savedFlower = savedFlower
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            LockerUiState()
        )

    fun onEvent(event: LockerEvent) {
        viewModelScope.launch {
            when (event) {
                is LockerEvent.SetShowDetail -> _uiState.update { it.copy(isShowDetail = event.isShown) }
                is LockerEvent.UpdateLocker -> updateFlower(
                    isSaved = event.isSaved,
                    flower = event.flowerDetail
                )
            }
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