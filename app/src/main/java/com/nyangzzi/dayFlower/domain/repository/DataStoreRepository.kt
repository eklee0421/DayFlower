package com.nyangzzi.dayFlower.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getRecentName(): Flow<List<String>>
    fun getRecentMean(): Flow<List<String>>

    suspend fun updateRecentName(list: List<String>)
    suspend fun updateRecentMean(list: List<String>)

}