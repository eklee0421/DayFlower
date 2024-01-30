package com.nyangzzi.dayFlower.domain.usecase.firebase

import com.google.firebase.auth.FirebaseUser
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: GetFirebaseRepository,
) {
    operator fun invoke(): Flow<FirebaseUser?> =
        repository.getUser()
}