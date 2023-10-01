package com.nyang.dayFlower.presentation.feature.mainFlower

sealed class MainFlowerEvent {
    object SearchPrevMainFlower : MainFlowerEvent()
    object SearchNextMainFlower : MainFlowerEvent()
    object ShowDatePicker: MainFlowerEvent()
    data class IsChangeView(val isCalendar: Boolean,val month : Int? = null, val day : Int? = null): MainFlowerEvent()
    data class SearchMainFlower(val month : Int, val day : Int): MainFlowerEvent()
}