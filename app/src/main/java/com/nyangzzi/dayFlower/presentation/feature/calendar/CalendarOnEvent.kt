package com.nyangzzi.dayFlower.presentation.feature.calendar

sealed class CalendarOnEvent {
    object OnPrevMonth : CalendarOnEvent()
    object OnNextMonth : CalendarOnEvent()
    data class SetDate(val year: Int, val month: Int) : CalendarOnEvent()
    object OnSearchMonth : CalendarOnEvent()
    data class SetDetailDialog(val isShown: Boolean) : CalendarOnEvent()
    data class SetDatePickerDialog(val isShown: Boolean) : CalendarOnEvent()
}