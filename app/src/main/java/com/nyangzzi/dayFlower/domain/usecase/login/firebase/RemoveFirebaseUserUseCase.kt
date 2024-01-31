package com.nyangzzi.dayFlower.domain.usecase.login.firebase

import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import javax.inject.Inject

class RemoveFirebaseUserUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke() =
        repository.removeFirebaseUser()
}