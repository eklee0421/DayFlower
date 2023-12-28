package com.nyang.dayFlower.presentation.feature.home

import com.nyang.dayFlower.domain.model.common.FlowerDetail
import java.time.LocalDate

data class HomeUiState(
    val flowerDetail: FlowerDetail = FlowerDetail(),
    val localDate: LocalDate = LocalDate.now(),
)