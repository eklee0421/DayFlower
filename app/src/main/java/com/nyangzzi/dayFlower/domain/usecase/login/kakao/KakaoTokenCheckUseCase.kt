package com.nyangzzi.dayFlower.domain.usecase.login.kakao

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoTokenCheckUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(): Flow<ResultWrapper<Unit>> =
        repository.kakaoTokenCheck()
}