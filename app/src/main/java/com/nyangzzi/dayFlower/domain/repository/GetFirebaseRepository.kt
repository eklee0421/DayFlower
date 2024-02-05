package com.nyangzzi.dayFlower.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import kotlinx.coroutines.flow.Flow

interface GetFirebaseRepository {
    suspend fun loadApp(): Flow<ResultWrapper<Unit>>
    fun locker(): Flow<List<FlowerDetail>>
    fun searchWords(): Flow<List<String>>
    suspend fun getSearchWords(): Flow<ResultWrapper<List<String>>>
    fun getUser(): Flow<FirebaseUser?>
    suspend fun setLocker(flower: FlowerDetail): Flow<ResultWrapper<Unit>>
    suspend fun removeLocker(dataNo: Int): Flow<ResultWrapper<Unit>>
    suspend fun getLocker(): Flow<ResultWrapper<List<FlowerDetail>>>
    fun checkIsSaved(dataNo: Int): Flow<Boolean>

}