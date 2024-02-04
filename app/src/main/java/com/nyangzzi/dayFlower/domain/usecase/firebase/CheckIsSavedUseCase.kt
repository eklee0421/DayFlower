package com.nyangzzi.dayFlower.domain.usecase.firebase

import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import javax.inject.Inject

class CheckIsSavedUseCase @Inject constructor(
    private val repository: GetFirebaseRepository
) {
    suspend operator fun invoke(dataNo: Int) = repository.checkIsSaved(dataNo)
}