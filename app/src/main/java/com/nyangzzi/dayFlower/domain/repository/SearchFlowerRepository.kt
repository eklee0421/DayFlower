package com.nyangzzi.dayFlower.domain.repository

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerDay.RequestFlowerDay
import com.nyangzzi.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerList.RequestFlowerList
import com.nyangzzi.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import kotlinx.coroutines.flow.Flow

interface SearchFlowerRepository {
    suspend fun getFlowerDetail(requestFlowerDetail: RequestFlowerDetail): Flow<ResultWrapper<FlowerDetail>>
    suspend fun getFlowerDay(requestFlowerDay: RequestFlowerDay): Flow<ResultWrapper<FlowerDetail>>
    suspend fun getFlowerMonth(requestFlowerMonth: RequestFlowerMonth): Flow<ResultWrapper<List<FlowerDetail>>>
    suspend fun getFlowerList(requestFlowerList: RequestFlowerList): Flow<ResultWrapper<List<FlowerDetail>>>

}