package com.nyangzzi.dayFlower.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetFirebaseRepository {
    suspend fun recommendedSearchWords(): Flow<ResultWrapper<List<String>>>
    fun getUser(): Flow<FirebaseUser?>
}