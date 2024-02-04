package com.nyangzzi.dayFlower.presentation.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.User
import com.nyangzzi.dayFlower.domain.usecase.datastore.ClearRecentWordUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.GetUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.LoadAppUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.firebase.CreateFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.firebase.LoginFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.firebase.UpdateFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.kakao.KakaoLoginUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.naver.NaverLoginUseCase
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
    private val loginFirebaseUserUseCase: LoginFirebaseUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateFirebaseUserUseCase: UpdateFirebaseUserUseCase,
    private val clearRecentWordUseCase: ClearRecentWordUseCase,
    private val loadAppUseCase: LoadAppUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    init {
        viewModelScope.launch {
            checkAutoLogin()
        }
    }

    fun onEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.KakaoLogin -> {
                    kakaoLogin()
                }

                is LoginEvent.NaverLogin -> {
                    naverLogin()
                }

                LoginEvent.ClearToastMsg -> _uiState.update {
                    it.copy(
                        toastMsg = null
                    )
                }

                LoginEvent.ClearDataStore -> clearSearchWord()
                LoginEvent.LoadApp -> loadApp()
            }
        }
    }

    private suspend fun loadApp() {
        loadAppUseCase().collect { result ->
            when (result) {
                ResultWrapper.Loading -> {
                    _uiState.update {
                        it.copy(
                            isBtnVisible = false,
                            bottomMsg = "정보를 로딩 중입니다"
                        )
                    }
                }

                is ResultWrapper.Success -> _uiState.update {
                    it.copy(
                        isNextScreen = true,
                        toastMsg = "환영합니다!"
                    )
                }

                else -> {}
            }

        }
    }

    private suspend fun clearSearchWord() {
        clearRecentWordUseCase.clearWord()
    }

    private suspend fun checkAutoLogin() {
        getUserUseCase().collect { user ->
            if (user == null) {
                _uiState.update { it.copy(isBtnVisible = true, bottomMsg = "") }
            } else {
                _uiState.update {
                    it.copy(
                        isSuccessLogin = true,
                        //toastMsg = "${user.displayName}님, 환영합니다!"
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
                    loginFirebaseUser(user, result.data)
                }

                ResultWrapper.None -> {}
            }
        }
    }

    private suspend fun updateFirebaseUser(user: User) {
        updateFirebaseUserUseCase(user).collect { result ->
            when (result) {
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(toastMsg = result.errorMessage, isBtnVisible = true) }
                }

                ResultWrapper.Loading -> {
                    _uiState.update {
                        it.copy(
                            isBtnVisible = false,
                            bottomMsg = "사용자 정보를 업데이트 중입니다"
                        )
                    }
                }

                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(
                            isSuccessLogin = true
                        )
                    }
                    //Log.d("check", "${result.data}")

                }

                ResultWrapper.None -> {}
            }
        }
    }

    private suspend fun loginFirebaseUser(user: User, isFirst: Boolean) {
        loginFirebaseUserUseCase(user).collect { result ->
            when (result) {
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(toastMsg = result.errorMessage, isBtnVisible = true) }
                }

                ResultWrapper.Loading -> {
                    _uiState.update { it.copy(isBtnVisible = false, bottomMsg = "로그인 중입니다..") }
                }

                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(bottomMsg = "사용자 로그인에 성공했습니다") }
                    Log.d("login", "${result.data}")
                    if (isFirst) updateFirebaseUser(user)
                    else _uiState.update {
                        it.copy(
                            //toastMsg = "${user.nickname}님, 환영합니다!",
                            isSuccessLogin = true
                        )
                    }
                }

                ResultWrapper.None -> {}
            }
        }
    }
}