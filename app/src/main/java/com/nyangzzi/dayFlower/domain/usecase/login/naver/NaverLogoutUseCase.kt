package com.nyangzzi.dayFlower.domain.usecase.login.naver

import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import javax.inject.Inject

class NaverLogoutUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke() =
        repository.naverLogout()
}