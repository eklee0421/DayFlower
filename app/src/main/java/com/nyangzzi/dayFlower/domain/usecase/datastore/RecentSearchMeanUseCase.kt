package com.nyangzzi.dayFlower.domain.usecase.datastore

import com.nyangzzi.dayFlower.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentSearchMeanUseCase @Inject constructor(
    private val repository: DataStoreRepository,
) {
    val recentMean: Flow<List<String>>
        get() = repository.getRecentMean()

    suspend fun setRentMean(list: List<String>) = repository.updateRecentMean(list)
}