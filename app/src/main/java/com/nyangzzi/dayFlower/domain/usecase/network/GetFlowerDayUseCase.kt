package com.nyangzzi.dayFlower.domain.usecase.network

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerDay.RequestFlowerDay
import com.nyangzzi.dayFlower.domain.repository.SearchFlowerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFlowerDayUseCase @Inject constructor(
    private val repository: SearchFlowerRepository
) {
    suspend operator fun invoke(requestFlowerDay: RequestFlowerDay): Flow<ResultWrapper<FlowerDetail>> =
        repository.getFlowerDay(requestFlowerDay)

}