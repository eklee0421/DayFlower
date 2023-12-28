package com.nyang.dayFlower.presentation.feature.home

import com.nyang.dayFlower.data.network.ResultWrapper
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import java.time.LocalDate

data class HomeUiState(
    val flowerDetail: ResultWrapper<FlowerDetail> = ResultWrapper.Loading,
    val localDate: LocalDate = LocalDate.now(),
)