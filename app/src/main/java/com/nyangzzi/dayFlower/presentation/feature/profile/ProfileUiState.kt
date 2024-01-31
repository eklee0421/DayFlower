package com.nyangzzi.dayFlower.presentation.feature.profile

data class ProfileUiState(
    val nickname: String = "",
    val platform: String = "",
    val email: String = "",
    val imgUrl: String? = null,

    val toastMsg: String? = null,
    val isLogoutSuccess: Boolean = false,

    val isRemoveDialog: Boolean = false,
    val isLogoutDialog: Boolean = false
)