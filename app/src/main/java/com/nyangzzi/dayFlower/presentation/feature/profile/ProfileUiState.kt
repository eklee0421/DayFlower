package com.nyangzzi.dayFlower.presentation.feature.profile

data class ProfileUiState (
    val nickname: String = "",
    val platform: String = "",
    val email: String = "",
    val imgUrl: String? = null,

    val toastMsg: String? = null,
    val isLogout: Boolean = false
)