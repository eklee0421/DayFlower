package com.nyangzzi.dayFlower.presentation.feature.home

sealed class HomeEvent {
    object GetFlowerDetail : HomeEvent()
    data class SetShowDetail(val isShown: Boolean) : HomeEvent()
}