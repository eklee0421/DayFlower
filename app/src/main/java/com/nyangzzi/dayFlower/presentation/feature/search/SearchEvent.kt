package com.nyangzzi.dayFlower.presentation.feature.search

sealed class SearchEvent {
    object SearchFlowerList : SearchEvent()
    object ClearFlowerList : SearchEvent()
    data class UpdateSelectedType(val selectedType: SearchTapType) : SearchEvent()
    data class UpdateSearchWord(val text: String) : SearchEvent()
    data class SetShowDetail(val isShown: Boolean) : SearchEvent()
}