package com.nyangzzi.dayFlower.domain.usecase.firebase

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLockerUseCase @Inject constructor(
    private val repository: GetFirebaseRepository,
) {
    suspend operator fun invoke(): Flow<ResultWrapper<List<FlowerDetail>>> =
        repository.getLocker()
}