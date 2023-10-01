package com.nyang.dayFlower.data.service

import com.nyang.dayFlower.domain.model.flowerDetail.ResponseFlowerDetail
import com.nyang.dayFlower.domain.model.flowerMonth.ResponseFlowerMonth
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchFlowerService {
    @GET("selectTodayFlower01?")
    suspend fun getFlowerDetail(
        @Query("serviceKey") serviceKey: String,
        @Query("fMonth") fMonth: Int,
        @Query("fDay") fDay: Int
    ): Response<ResponseFlowerDetail>

    @GET("selectTodayFlowerList01?")
    suspend fun getFlowerMonth(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows:Int,
        @Query("fMonth") fMonth: Int,
    ): Response<ResponseFlowerMonth>
}