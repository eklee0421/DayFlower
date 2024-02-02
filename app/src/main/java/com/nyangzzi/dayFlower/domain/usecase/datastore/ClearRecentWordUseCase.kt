package com.nyangzzi.dayFlower.domain.usecase.datastore

import com.nyangzzi.dayFlower.domain.repository.DataStoreRepository
import javax.inject.Inject

class ClearRecentWordUseCase @Inject constructor(
    private val repository: DataStoreRepository,
) {
    suspend fun clearWord() {
        repository.updateRecentMean(emptyList())
        repository.updateRecentName(emptyList())
    }
}