package com.nyangzzi.dayFlower.presentation.feature.search

sealed class SearchEvent {
    object SearchFlowerList : SearchEvent()
    data class UpdateSelectedType(val selectedType: SearchTapType) : SearchEvent()
    data class UpdateSearchWord(val text: String) : SearchEvent()
}