package com.nyangzzi.dayFlower.presentation.feature.locker

import com.google.firebase.auth.FirebaseUser
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail

data class LockerUiState(
    val user: FirebaseUser? = null,
    val isShowDetail: Boolean = false,
    val saveFlower: List<FlowerDetail> = emptyList()
)