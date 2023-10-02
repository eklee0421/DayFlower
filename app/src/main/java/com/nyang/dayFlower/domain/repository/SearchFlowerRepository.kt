package com.nyang.dayFlower.domain.repository

import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.model.flowerList.RequestFlowerList
import com.nyang.dayFlower.domain.model.flowerMonth.RequestFlowerMonth

interface SearchFlowerRepository {
    suspend fun getFlowerDetail(requestFlowerDetail: RequestFlowerDetail) : FlowerDetail
    suspend fun getFlowerMonth(requestFlowerMonth: RequestFlowerMonth): List<FlowerDetail>
    suspend fun getFlowerList(requestFlowerList: RequestFlowerList): List<FlowerDetail>
}