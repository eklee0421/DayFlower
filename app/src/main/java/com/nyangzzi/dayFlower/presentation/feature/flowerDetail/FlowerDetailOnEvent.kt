package com.nyangzzi.dayFlower.presentation.feature.flowerDetail

sealed class FlowerDetailOnEvent {
    data class OnSearchDetail(val dataNo: Int?) : FlowerDetailOnEvent()
}