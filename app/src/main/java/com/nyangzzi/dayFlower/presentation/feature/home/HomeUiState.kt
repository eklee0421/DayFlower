package com.nyangzzi.dayFlower.presentation.feature.home

import com.google.firebase.auth.FirebaseUser
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import java.time.LocalDate

data class HomeUiState(
    val flowerDetail: ResultWrapper<FlowerDetail> = ResultWrapper.Loading,
    val localDate: LocalDate = LocalDate.now(),
    val isShowDetail: Boolean = false,
    val user: FirebaseUser? = null,
    val savedFlower: List<FlowerDetail> = emptyList()
)