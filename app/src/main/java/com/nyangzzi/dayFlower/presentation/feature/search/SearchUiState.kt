package com.nyangzzi.dayFlower.presentation.feature.search

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail

data class SearchUiState(
    val flowerList: ResultWrapper<List<FlowerDetail>> = ResultWrapper.None,
    val selectedType: SearchTapType = SearchTapType.NAME,
    val searchWord: String = "",
    val isShowDetail: Boolean = false,
    val recommendedWords: List<String> = emptyList(),
    val recentName: List<String> = emptyList(),
    val recentMean: List<String> = emptyList(),
    val savedFlower: List<FlowerDetail> = emptyList()
)

enum class SearchTapType(val type: Int, val title: String) {
    NAME(type = 1, title = "꽃이름"),
    MEAN(type = 4, title = "꽃말")
}