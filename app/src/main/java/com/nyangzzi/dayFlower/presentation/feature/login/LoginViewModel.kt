package com.nyangzzi.dayFlower.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.usecase.KakaoLoginUseCase
import com.nyangzzi.dayFlower.domain.usecase.NaverLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val naverLoginUseCase: NaverLoginUseCase
) : ViewModel() {


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

    suspend fun kakaoLogin() {
        kakaoLoginUseCase().collect { result ->
            when (result) {
                is ResultWrapper.Error -> {

                }

                ResultWrapper.Loading -> {

                }

                is ResultWrapper.Success -> {

                }
            }
        }
    }

    suspend fun naverLogin() {
        naverLoginUseCase().collect { result ->
            when (result) {
                is ResultWrapper.Error -> {

                }

                ResultWrapper.Loading -> {

                }

                is ResultWrapper.Success -> {

                }
            }
        }
    }
}