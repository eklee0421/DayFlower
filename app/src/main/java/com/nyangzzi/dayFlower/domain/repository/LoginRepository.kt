package com.nyangzzi.dayFlower.domain.repository

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun kaKaoLogin(): Flow<ResultWrapper<User>>
    suspend fun naverLogin(): Flow<ResultWrapper<User>>
    suspend fun createFirebaseUser(user: User): Flow<ResultWrapper<Unit>>
    suspend fun loginFirebaseUser(user: User): Flow<ResultWrapper<User>>
}