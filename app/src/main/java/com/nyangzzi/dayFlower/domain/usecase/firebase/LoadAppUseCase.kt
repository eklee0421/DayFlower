package com.nyangzzi.dayFlower.domain.usecase.firebase

import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import javax.inject.Inject

class LoadAppUseCase @Inject constructor(
    private val firebaseRepository: GetFirebaseRepository
) {
    suspend operator fun invoke() = firebaseRepository.loadApp()
}