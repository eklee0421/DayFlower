package com.nyangzzi.dayFlower.data.repository

import com.nyangzzi.dayFlower.data.base.DataStoreSource
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStoreSource: DataStoreSource
) : DataStoreRepository {

    companion object {
        private const val RECENT_NAME_KEY = "recent_name"
        private const val RECENT_MEAN_KEY = "recent_mean"

        private const val SPLITTER = "/&&/"

        private val _recentViewFlowerList = MutableStateFlow<List<FlowerDetail>>(emptyList())
        val recentViewFlowerList: StateFlow<List<FlowerDetail>> get() = _recentViewFlowerList

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRecentName(): Flow<List<String>> =
        dataStoreSource.getValue(RECENT_NAME_KEY).flatMapConcat {
            flow {
                emit(if (it.isNullOrEmpty()) emptyList() else it.split(SPLITTER))
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRecentMean(): Flow<List<String>> =
        dataStoreSource.getValue(RECENT_MEAN_KEY).flatMapConcat {
            flow {
                emit(if (it.isNullOrEmpty()) emptyList() else it.split(SPLITTER))
            }
        }

    override suspend fun updateRecentName(list: List<String>) {
        dataStoreSource.setValue(key = RECENT_NAME_KEY, value = list.joinToString(SPLITTER))
    }

    override suspend fun updateRecentMean(list: List<String>) {
        dataStoreSource.setValue(key = RECENT_MEAN_KEY, value = list.joinToString(SPLITTER))
    }

    override suspend fun updateViewFlower(flower: FlowerDetail) {
        val recentFlower = recentViewFlowerList.value.toMutableList().apply {
            add(0, flower)
        }.toList().distinct()

        _recentViewFlowerList.value = recentFlower.subList(0, Integer.min(10, recentFlower.size))
    }

    override fun getRecentViewFlower(): Flow<List<FlowerDetail>> = recentViewFlowerList

}