package com.nyangzzi.dayFlower.presentation.feature.home

sealed class HomeEvent {
    object GetDayFlower : HomeEvent()
    data class SetShowDetail(val isShown: Boolean) : HomeEvent()
}