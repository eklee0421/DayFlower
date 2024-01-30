package com.nyangzzi.dayFlower.presentation.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.domain.usecase.firebase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _user = getUserUseCase()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = combine(_uiState, _user) { state, user ->
        state.copy(
            nickname = user?.displayName ?: "",
            imgUrl = user?.photoUrl.toString()
            //userMsg = user?.
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ProfileUiState()
    )

    fun onEvent(event: ProfileEvent) {
        when (event) {

            else -> {}
        }
    }


}