package com.nyangzzi.dayFlower.presentation.feature.login

data class LoginUiState(
    val toastMsg: String? = null,
    val bottomMsg: String? = null,
    val isBtnVisible: Boolean = true,
    val isSuccessLogin: Boolean = false
)