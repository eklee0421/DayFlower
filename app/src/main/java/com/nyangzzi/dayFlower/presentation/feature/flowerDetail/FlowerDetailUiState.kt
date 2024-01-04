package com.nyangzzi.dayFlower.presentation.feature.flowerDetail

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail

const val None = "-1"

data class FlowerDetailUiState(
    val dataNo: String = None,
    val flowerDetail: ResultWrapper<FlowerDetail> = ResultWrapper.Loading
)