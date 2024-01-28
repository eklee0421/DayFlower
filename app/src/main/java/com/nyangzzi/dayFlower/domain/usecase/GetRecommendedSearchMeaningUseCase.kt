package com.nyangzzi.dayFlower.domain.usecase

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendedSearchMeaningUseCase @Inject constructor(
    private val repository: GetFirebaseRepository,
) {
    suspend operator fun invoke(): Flow<ResultWrapper<List<String>>> =
        repository.recommendedSearchWords()
}