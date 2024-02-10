package com.nyangzzi.dayFlower.data.repository

import com.nyangzzi.dayFlower.data.base.DataStoreSource
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStoreSource: DataStoreSource
) : DataStoreRepository {

    companion object {
        private const val recentNameKey = "recent_name"
        private const val recentMeanKey = "recent_mean"

        private const val splitter = "/&&/"

        private var recentViewFlowerList: List<FlowerDetail> = emptyList()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRecentName(): Flow<List<String>> =
        dataStoreSource.getValue(recentNameKey).flatMapConcat {
            flow {
                emit(if (it.isNullOrEmpty()) emptyList() else it.split(splitter))
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRecentMean(): Flow<List<String>> =
        dataStoreSource.getValue(recentMeanKey).flatMapConcat {
            flow {
                emit(if (it.isNullOrEmpty()) emptyList() else it.split(splitter))
            }
        }

    override suspend fun updateRecentName(list: List<String>) {
        dataStoreSource.setValue(key = recentNameKey, value = list.joinToString(splitter))
    }

    override suspend fun updateRecentMean(list: List<String>) {
        dataStoreSource.setValue(key = recentMeanKey, value = list.joinToString(splitter))
    }

    override suspend fun updateViewFlower(flower: FlowerDetail) {
        val recentFlower = recentViewFlowerList.toMutableList().apply {
            add(0, flower)
        }.toList().distinct()

        recentViewFlowerList = recentFlower.subList(0, Integer.min(10, recentFlower.size))
    }

    override fun getRecentViewFlower(): Flow<List<FlowerDetail>> = flow {
        while (true) {
            emit(recentViewFlowerList)
            kotlinx.coroutines.delay(1000)
        }
    }.flowOn(Dispatchers.IO)

}