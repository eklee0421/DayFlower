package com.nyangzzi.dayFlower.presentation.feature.locker

import com.google.firebase.auth.FirebaseUser

data class LockerUiState(
    val user: FirebaseUser? = null,
    val isShowDetail: Boolean = false
)