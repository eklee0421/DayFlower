package com.nyang.dayFlower.domain.usecase

import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyang.dayFlower.domain.repository.SearchFlowerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFlowerMonthUseCase @Inject constructor(
    private val repository: SearchFlowerRepository
){
    suspend operator fun invoke(requestFlowerMonth: RequestFlowerMonth) : Flow<List<FlowerDetail>> = flow{
        emit(repository.getFlowerMonth(requestFlowerMonth))
    }
}