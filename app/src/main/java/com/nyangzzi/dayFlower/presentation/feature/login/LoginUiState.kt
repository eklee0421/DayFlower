package com.nyangzzi.dayFlower.presentation.feature.login

data class LoginUiState(
    val toastMsg: String? = null,
    val bottomMsg: String? = "자동 로그인 중입니다",
    val isBtnVisible: Boolean = false,
    val isSuccessLogin: Boolean = false,
)