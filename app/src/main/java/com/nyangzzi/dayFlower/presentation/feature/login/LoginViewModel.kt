package com.nyangzzi.dayFlower.presentation.feature.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    suspend fun kakaoLogin() {
        kakaoLoginUseCase(context = application.applicationContext).collect { result ->
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