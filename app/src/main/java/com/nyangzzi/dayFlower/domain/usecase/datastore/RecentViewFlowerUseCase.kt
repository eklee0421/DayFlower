package com.nyangzzi.dayFlower.domain.usecase.datastore

import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentViewFlowerUseCase @Inject constructor(
    private val repository: DataStoreRepository,
) {
    val recentViewFlower: Flow<List<FlowerDetail>>
        get() = repository.getRecentViewFlower()

    suspend fun setRecentViewFlower(flower: FlowerDetail) = repository.updateViewFlower(flower)
}