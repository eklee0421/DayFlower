package com.nyang.dayFlower.presentation.feature.mainFlower

sealed class MainFlowerEvent {
    object SearchPrevDay : MainFlowerEvent()
    object SearchNextDay : MainFlowerEvent()
    object SearchPrevMonth : MainFlowerEvent()
    object SearchNextMonth : MainFlowerEvent()
    object ShowDatePicker: MainFlowerEvent()
    data class IsSearchDialog(val isShow: Boolean): MainFlowerEvent()
    data class SearchFlowerList(val type:Int, val word: String):MainFlowerEvent()
    data class IsChangeView(val isCalendar: Boolean, val month : Int? = null, val day : Int? = null): MainFlowerEvent()
    data class SearchMainFlower(val month : Int, val day : Int): MainFlowerEvent()
}