package com.nyangzzi.dayFlower.domain.repository

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun kaKaoLogin(): Flow<ResultWrapper<Unit>>
    suspend fun NaverLogin(): Flow<ResultWrapper<Unit>>
}