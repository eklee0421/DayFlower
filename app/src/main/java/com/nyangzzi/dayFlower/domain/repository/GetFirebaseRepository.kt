package com.nyangzzi.dayFlower.domain.repository

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetFirebaseRepository {
    suspend fun recommendedSearchWords(): Flow<ResultWrapper<List<String>>>
}