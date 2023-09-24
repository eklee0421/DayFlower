package com.nyang.dayFlower.data.service

import com.nyang.dayFlower.domain.model.flowerDetail.ResponseFlowerDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchFlowerService {
    @GET("selectTodayFlower01?")
    fun getFlowerDetail(
        @Query("serviceKey") serviceKey: String,
        @Query("fMonth") fMonth: Int,
        @Query("fDay") fDay: Int
    ): Call<ResponseFlowerDetail>
}