package com.nyangzzi.dayFlower.presentation.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.User
import com.nyangzzi.dayFlower.domain.usecase.login.CreateFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.KakaoLoginUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.LoginFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.NaverLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val naverLoginUseCase: NaverLoginUseCase,
    private val createFirebaseUserUseCase: CreateFirebaseUserUseCase,
    private val loginFirebaseUserUseCase: LoginFirebaseUserUseCase
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

                LoginEvent.clearToasMsg -> _uiState.update {
                    it.copy(
                        toastMsg = null
                    )
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
                    _uiState.update {
                        it.copy(
                            isBtnVisible = false,
                            bottomMsg = "카카오 인증 정보를 확인 중입니다"
                        )
                    }
                }

                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(bottomMsg = "카카오 인증에 성공했습니다", isBtnVisible = false) }

                    Log.d("kakao", "${result.data}")

                    createFirebaseUser(result.data)
                }

                ResultWrapper.None -> {}
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
                    _uiState.update {
                        it.copy(
                            isBtnVisible = false,
                            bottomMsg = "네이버 인증 정보를 확인 중입니다"
                        )
                    }
                }

                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(bottomMsg = "네이버 인증에 성공했습니다", isBtnVisible = false) }
                    Log.d("naver", "${result.data}")

                    createFirebaseUser(result.data)
                }

                ResultWrapper.None -> {}
            }
        }
    }

    private suspend fun createFirebaseUser(user: User) {
        createFirebaseUserUseCase(user).collect { result ->
            when (result) {
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(toastMsg = result.errorMessage, isBtnVisible = true) }
                }

                ResultWrapper.Loading -> {
                    _uiState.update { it.copy(isBtnVisible = false, bottomMsg = "사용자 정보를 확인중입니다") }
                }

                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(bottomMsg = "사용자 정보 확인에 성공했습니다") }
                    //Log.d("check", "${result.data}")
                    loginFirebaseUser(user)
                }

                ResultWrapper.None -> {}
            }
        }
    }

    private suspend fun loginFirebaseUser(user: User) {
        loginFirebaseUserUseCase(user).collect { result ->
            when (result) {
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(toastMsg = result.errorMessage, isBtnVisible = true) }
                }

                ResultWrapper.Loading -> {
                    _uiState.update { it.copy(isBtnVisible = false, bottomMsg = "로그인 중입니다..") }
                }

                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(toastMsg = "환영합니다!", isSuccessLogin = true) }
                    Log.d("login", "${result.data}")
                }

                ResultWrapper.None -> {}
            }
        }
    }
}