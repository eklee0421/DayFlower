package com.nyangzzi.dayFlower.domain.repository

import android.content.Context
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun kaKaoLogin(context: Context): Flow<ResultWrapper<Unit>>
}