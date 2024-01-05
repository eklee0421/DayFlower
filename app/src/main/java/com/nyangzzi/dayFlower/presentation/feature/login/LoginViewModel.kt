package com.nyangzzi.dayFlower.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.usecase.KakaoLoginUseCase
import com.nyangzzi.dayFlower.domain.usecase.NaverLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val naverLoginUseCase: NaverLoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.kakaoLogin -> {
                    kakaoLogin()
                }

                is LoginEvent.naverLogin -> {
                    naverLogin()
                }
            }
        }
    }

    private suspend fun kakaoLogin() {
        kakaoLoginUseCase().collect { result ->
            when (result) {
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(toastMsg = result.errorMessage, isBtnVisible = true) }
                }

                ResultWrapper.Loading -> {
                    _uiState.update { it.copy(isBtnVisible = false) }
                }

                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(toastMsg = "카카오 로그인에 성공했습니다.") }
                }
            }
        }
    }

    private suspend fun naverLogin() {
        naverLoginUseCase().collect { result ->
            when (result) {
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(toastMsg = result.errorMessage, isBtnVisible = true) }
                }

                ResultWrapper.Loading -> {
                    _uiState.update { it.copy(isBtnVisible = false) }
                }

                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(toastMsg = "네이버 로그인에 성공했습니다.") }
                }
            }
        }
    }
}