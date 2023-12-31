package com.nyangzzi.dayFlower.domain.usecase

import android.content.Context
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(context: Context): Flow<ResultWrapper<Unit>> =
        repository.kaKaoLogin(context = context)
}