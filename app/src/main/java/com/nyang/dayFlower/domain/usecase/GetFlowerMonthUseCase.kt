package com.nyang.dayFlower.domain.usecase

import com.nyang.dayFlower.data.network.ResultWrapper
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyang.dayFlower.domain.repository.SearchFlowerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFlowerMonthUseCase @Inject constructor(
    private val repository: SearchFlowerRepository
){
    suspend operator fun invoke(requestFlowerMonth: RequestFlowerMonth) : Flow<ResultWrapper<List<FlowerDetail>>> =
        repository.getFlowerMonth(requestFlowerMonth)

}