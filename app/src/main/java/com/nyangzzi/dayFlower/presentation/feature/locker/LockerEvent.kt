package com.nyangzzi.dayFlower.presentation.feature.locker

sealed class LockerEvent {
    data class SetShowDetail(val isShown: Boolean) : LockerEvent()
}