package com.nyang.dayFlower.data.service

import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object SearchFlowerManager {
    
    private const val BASE_URL = "https://apis.data.go.kr/1390804/NihhsTodayFlowerInfo01/"
    private fun getInstance() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        //.client(okHttpClient)
        .addConverterFactory(
            TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build())
        )
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.MINUTES)
        .readTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .build()

    fun getService(): SearchFlowerService = getInstance().create(SearchFlowerService::class.java)
}