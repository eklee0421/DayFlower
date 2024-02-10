package com.nyangzzi.dayFlower.presentation.feature.home

import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail

sealed class HomeEvent {
    object GetDayFlower : HomeEvent()
    data class SetShowDetail(val isShown: Boolean, val dataNo: Int? = null) : HomeEvent()
    data class UpdateLocker(val isSaved: Boolean, val flowerDetail: FlowerDetail) : HomeEvent()
}