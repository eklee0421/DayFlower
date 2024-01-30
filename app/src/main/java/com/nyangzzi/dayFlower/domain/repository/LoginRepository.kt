package com.nyangzzi.dayFlower.domain.repository

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun kaKaoLogin(): Flow<ResultWrapper<User>>
    suspend fun kakaoLogout()
    suspend fun naverLogin(): Flow<ResultWrapper<User>>
    suspend fun naverLogout()
    suspend fun createFirebaseUser(user: User): Flow<ResultWrapper<Boolean>>
    suspend fun loginFirebaseUser(user: User): Flow<ResultWrapper<User>>
    suspend fun logoutFirebaseUser(): Flow<ResultWrapper<Unit>>
    suspend fun updateFirebaseUser(user: User): Flow<ResultWrapper<Unit>>
}