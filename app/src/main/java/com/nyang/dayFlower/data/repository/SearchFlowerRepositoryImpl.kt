package com.nyang.dayFlower.data.repository

import android.util.Log
import com.nyang.dayFlower.BuildConfig
import com.nyang.dayFlower.data.service.SearchFlowerManager
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyang.dayFlower.domain.repository.SearchFlowerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchFlowerRepositoryImpl : SearchFlowerRepository {
    override suspend fun getFlowerDetail(requestFlowerDetail: RequestFlowerDetail): FlowerDetail =
        withContext(Dispatchers.IO) {
            val service = SearchFlowerManager.getService()
            var result = FlowerDetail()

            val data = service.getFlowerDetail(
                serviceKey = BuildConfig.search_flower_api_key,
                fMonth = requestFlowerDetail.fMonth ?: 1,
                fDay = requestFlowerDetail.fDay ?: 1
            )

            when (data.isSuccessful) {
                true -> {
                    result = data.body()?.root?.result ?: FlowerDetail()
                    Log.d("Success", "$result")
                }

                else -> {
                    Log.d("Failure", "get flower detail")
                }
            }

            return@withContext result
        }

    override suspend fun getFlowerMonth(requestFlowerMonth: RequestFlowerMonth): List<FlowerDetail> =
        withContext(Dispatchers.IO) {
            val service = SearchFlowerManager.getService()
            var result = emptyList<FlowerDetail>()

            val data = service.getFlowerMonth(
                serviceKey = BuildConfig.search_flower_api_key,
                numOfRows = 31,
                fMonth = requestFlowerMonth.fMonth ?: 1,

                )

            when (data.isSuccessful) {
                true -> {
                    result = data.body()?.root?.result ?: emptyList()
                    Log.d("Success", "$result")
                }

                else -> {
                    Log.d("Failure", "get flower detail")
                }
            }

            return@withContext result
        }
}