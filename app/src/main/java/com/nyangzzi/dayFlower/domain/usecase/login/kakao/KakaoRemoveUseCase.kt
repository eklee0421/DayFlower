package com.nyangzzi.dayFlower.domain.usecase.login.kakao

import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import javax.inject.Inject

class KakaoRemoveUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke() =
        repository.kakaoRemove()
}