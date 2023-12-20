package com.nyang.dayFlower.domain.repository

import com.nyang.dayFlower.data.network.ResultWrapper
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.model.flowerList.RequestFlowerList
import com.nyang.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import kotlinx.coroutines.flow.Flow

interface SearchFlowerRepository {
    suspend fun getFlowerDetail(requestFlowerDetail: RequestFlowerDetail) : Flow<ResultWrapper<FlowerDetail>>
    suspend fun getFlowerMonth(requestFlowerMonth: RequestFlowerMonth): Flow<ResultWrapper<List<FlowerDetail>>>
    suspend fun getFlowerList(requestFlowerList: RequestFlowerList): Flow<ResultWrapper<List<FlowerDetail>>>
}