package com.nyangzzi.dayFlower.domain.usecase.firebase

import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import javax.inject.Inject

class FirebaseManager @Inject constructor(
    private val repository: GetFirebaseRepository
) {
    suspend fun locker() = repository.locker()

    suspend fun searchWords() = repository.searchWords()
}