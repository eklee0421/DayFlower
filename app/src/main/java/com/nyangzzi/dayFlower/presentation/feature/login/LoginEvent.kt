package com.nyangzzi.dayFlower.presentation.feature.login

sealed class LoginEvent {
    object kakaoLogin : LoginEvent()
    object naverLogin : LoginEvent()
}