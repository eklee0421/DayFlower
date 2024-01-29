package com.nyangzzi.dayFlower.domain.usecase

import com.nyangzzi.dayFlower.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentSearchNameUseCase @Inject constructor(
    private val repository: DataStoreRepository,
) {
    val recentName: Flow<List<String>>
        get() = repository.getRecentName()

    suspend fun setRentName(list: List<String>) = repository.updateRecentName(list)
}