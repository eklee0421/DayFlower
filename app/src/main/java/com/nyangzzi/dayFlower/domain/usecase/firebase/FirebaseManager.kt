package com.nyangzzi.dayFlower.domain.usecase.firebase

import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseManager @Inject constructor(
    private val repository: GetFirebaseRepository
) {
    fun locker(): Flow<List<FlowerDetail>> = repository.locker()

    fun searchWords() = repository.searchWords()

    fun isSaved(dataNo: Int) = repository.checkIsSaved(dataNo)
}