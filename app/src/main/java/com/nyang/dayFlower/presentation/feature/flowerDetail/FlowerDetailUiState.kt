package com.nyang.dayFlower.presentation.feature.flowerDetail

import com.nyang.dayFlower.domain.model.common.FlowerDetail
import java.time.LocalDate

data class FlowerDetailUiState (
    val isDatePicker: Boolean = false,
    val flowerDetail : FlowerDetail = FlowerDetail(),
    val flowerMonth : List<FlowerDetail> = emptyList(),
    val localDate: LocalDate = LocalDate.now(),
    val isCalendar: Boolean = false
)