package com.nyang.dayFlower.presentation.feature.mainFlower

import com.nyang.dayFlower.domain.model.common.FlowerDetail
import java.time.LocalDate

data class MainFlowerUiState (
    val isDatePicker: Boolean = false,
    val isSearch: Boolean = false,
    val flowerDetail : FlowerDetail = FlowerDetail(),
    val flowerMonth : List<FlowerDetail> = emptyList(),
    val flowerList : List<FlowerDetail> = emptyList(),
    val localDate: LocalDate = LocalDate.now(),
    val isCalendar: Boolean = false
)