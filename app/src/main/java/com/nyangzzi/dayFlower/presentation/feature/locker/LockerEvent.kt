package com.nyangzzi.dayFlower.presentation.feature.locker

import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail

sealed class LockerEvent {
    data class SetShowDetail(val isShown: Boolean) : LockerEvent()
    data class UpdateLocker(val isSaved: Boolean, val flowerDetail: FlowerDetail) : LockerEvent()
}