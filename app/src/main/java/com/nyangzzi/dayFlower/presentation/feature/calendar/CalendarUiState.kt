package com.nyangzzi.dayFlower.presentation.feature.calendar

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import java.time.LocalDate

data class CalendarUiState(
    val localDate: LocalDate = LocalDate.now(),
    val flowerMonth: ResultWrapper<List<FlowerDetail>> = ResultWrapper.Loading,
    val isDetail: Boolean = false,
    val isDatePicker: Boolean = false
)