package com.nyangzzi.dayFlower.domain.usecase.network


import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyangzzi.dayFlower.domain.repository.SearchFlowerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFlowerDetailUseCase @Inject constructor(
    private val repository: SearchFlowerRepository
) {
    suspend operator fun invoke(requestFlowerDetail: RequestFlowerDetail): Flow<ResultWrapper<FlowerDetail>> =
        repository.getFlowerDetail(requestFlowerDetail)

}