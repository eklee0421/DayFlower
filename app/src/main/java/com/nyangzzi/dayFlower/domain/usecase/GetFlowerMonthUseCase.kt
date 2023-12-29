package com.nyangzzi.dayFlower.domain.usecase

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyangzzi.dayFlower.domain.repository.SearchFlowerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFlowerMonthUseCase @Inject constructor(
    private val repository: SearchFlowerRepository
) {
    suspend operator fun invoke(requestFlowerMonth: RequestFlowerMonth): Flow<ResultWrapper<List<FlowerDetail>>> =
        repository.getFlowerMonth(requestFlowerMonth)

}