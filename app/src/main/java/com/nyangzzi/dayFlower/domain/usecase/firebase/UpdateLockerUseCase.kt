package com.nyangzzi.dayFlower.domain.usecase.firebase

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateLockerUseCase @Inject constructor(
    private val repository: GetFirebaseRepository,
) {
    suspend operator fun invoke(
        isSaved: Boolean,
        flower: FlowerDetail
    ): Flow<ResultWrapper<List<FlowerDetail>>> =
        flow {

            if (isSaved) {
                flower.dataNo?.let {
                    repository.removeLocker(it).collect { result ->
                        when (result) {
                            is ResultWrapper.Error -> {
                                emit(ResultWrapper.Error(result.errorMessage))
                            }

                            ResultWrapper.Loading -> emit(ResultWrapper.Loading)
                            ResultWrapper.None -> emit(ResultWrapper.None)
                            is ResultWrapper.Success -> {
                                repository.getLocker().collect {
                                    emit(it)
                                }
                            }
                        }

                    }
                }
            } else {
                repository.setLocker(flower).collect { result ->
                    when (result) {
                        is ResultWrapper.Error -> {
                            emit(ResultWrapper.Error(result.errorMessage))
                        }

                        ResultWrapper.Loading -> emit(ResultWrapper.Loading)
                        ResultWrapper.None -> emit(ResultWrapper.None)
                        is ResultWrapper.Success -> {
                            repository.getLocker().collect {
                                emit(it)
                            }
                        }
                    }

                }
            }

        }

}