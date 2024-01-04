package com.nyangzzi.dayFlower.presentation.feature.flowerDetail

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail

data class FlowerDetailUiState(
    val flowerDetail: ResultWrapper<FlowerDetail> = ResultWrapper.Loading
)