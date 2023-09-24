package com.nyang.dayFlower.data.repository

import android.util.Log
import com.nyang.dayFlower.BuildConfig
import com.nyang.dayFlower.data.service.SearchFlowerManager
import com.nyang.dayFlower.domain.model.flowerDetail.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.ResponseFlowerDetail
import com.nyang.dayFlower.domain.repository.SearchFlowerRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFlowerRepositoryImpl : SearchFlowerRepository {
    override suspend fun getFlowerDetail(requestFlowerDetail: RequestFlowerDetail): FlowerDetail{
        val service = SearchFlowerManager.getService()
        var result = FlowerDetail()

        service.getFlowerDetail(
            serviceKey = BuildConfig.search_flower_api_key,
            fMonth = 9,
            fDay = 17
        ).enqueue(object : Callback<ResponseFlowerDetail> {
            override fun onResponse(
                call: Call<ResponseFlowerDetail>,
                response: Response<ResponseFlowerDetail>
            ) {
                Log.d("Success", response.body().toString())

                response.body()?.let {
                    result = it.root?.result ?: FlowerDetail()
                }
            }

            override fun onFailure(
                call: Call<ResponseFlowerDetail>,
                t: Throwable
            ) {
                Log.d("Failure", t.localizedMessage ?: "bbb")
            }

        })

        return result
    }
}