package com.nyangzzi.dayFlower.data.repository

import android.util.Log
import com.nyangzzi.dayFlower.BuildConfig
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.data.service.SearchFlowerManager
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerList.RequestFlowerList
import com.nyangzzi.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyangzzi.dayFlower.domain.repository.SearchFlowerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class SearchFlowerRepositoryImpl : SearchFlowerRepository {
    override suspend fun getFlowerDetail(requestFlowerDetail: RequestFlowerDetail): Flow<ResultWrapper<FlowerDetail>> =
        flow {

            SearchFlowerManager.getService().getFlowerDetail(
                serviceKey = BuildConfig.search_flower_api_key,
                fMonth = requestFlowerDetail.fMonth ?: 1,
                fDay = requestFlowerDetail.fDay ?: 1
            )
                .onSuccess {
                    emit(ResultWrapper.Success(it.root?.result ?: FlowerDetail()))
                }.onFailure {
                    Log.d("detail", it.message ?: "")
                    emit(ResultWrapper.Error(it.message ?: "error: Failure Get flower detail"))
                }

        }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)

    override suspend fun getFlowerMonth(requestFlowerMonth: RequestFlowerMonth): Flow<ResultWrapper<List<FlowerDetail>>> =
        flow {

            SearchFlowerManager.getService().getFlowerMonth(
                serviceKey = BuildConfig.search_flower_api_key,
                numOfRows = 31,
                fMonth = requestFlowerMonth.fMonth ?: 1,
            ).onSuccess {
                emit(ResultWrapper.Success(it.root?.result ?: emptyList()))
            }
                .onFailure {
                    emit(
                        ResultWrapper.Error(
                            it.message ?: "error: Failure Get flower detail"
                        )
                    )
                }

        }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)

    override suspend fun getFlowerList(requestFlowerList: RequestFlowerList): Flow<ResultWrapper<List<FlowerDetail>>> =
        flow {

            SearchFlowerManager.getService().getFlowerList(
                serviceKey = BuildConfig.search_flower_api_key,
                searchType = requestFlowerList.searchType ?: 1,
                searchWord = requestFlowerList.searchWord ?: "",
                pageNo = requestFlowerList.pageNo ?: 1,
                numOfRows = 10,
            ).onSuccess {
                emit(ResultWrapper.Success(it.root?.result ?: emptyList()))
            }
                .onFailure {
                    emit(
                        ResultWrapper.Error(
                            it.message ?: "error: Failure Get flower detail"
                        )
                    )
                }

        }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)

}