package com.nyangzzi.dayFlower.domain.repository

import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getRecentName(): Flow<List<String>>
    fun getRecentMean(): Flow<List<String>>
    fun getRecentViewFlower(): Flow<List<FlowerDetail>>

    suspend fun updateRecentName(list: List<String>)
    suspend fun updateRecentMean(list: List<String>)
    suspend fun updateViewFlower(flower: FlowerDetail)


}