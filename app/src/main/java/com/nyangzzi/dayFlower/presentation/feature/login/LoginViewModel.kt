package com.nyangzzi.dayFlower.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
) : ViewModel() {


    fun onEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.kakaoLogin -> {
                    kakaoLogin()
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
}