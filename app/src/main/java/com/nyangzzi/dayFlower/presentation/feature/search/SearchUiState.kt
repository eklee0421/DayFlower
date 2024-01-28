package com.nyangzzi.dayFlower.presentation.feature.search

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail

data class SearchUiState(
    val flowerList: ResultWrapper<List<FlowerDetail>> = ResultWrapper.None,
    val selectedType: SearchTapType = SearchTapType.NAME,
    val searchWord: String = "사랑",
    val isShowDetail: Boolean = false
)

enum class SearchTapType(val type: Int, val title: String) {
    NAME(type = 1, title = "꽃이름"),
    MEAN(type = 4, title = "꽃말")
}