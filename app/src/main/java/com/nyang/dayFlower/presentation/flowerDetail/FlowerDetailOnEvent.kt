package com.nyang.dayFlower.presentation.flowerDetail

sealed class FlowerDetailOnEvent {
    object SearchPrevFlower : FlowerDetailOnEvent()
    object SearchNextFlower : FlowerDetailOnEvent()
    data class SearchFlower(val month : Int, val day : Int): FlowerDetailOnEvent()
}