package com.nyangzzi.dayFlower.domain.usecase.login

import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import javax.inject.Inject

class KakaoLogoutUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke() =
        repository.kakaoLogout()
}