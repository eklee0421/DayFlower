package com.nyangzzi.dayFlower.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip

class GetFirebaseRepositoryImpl : GetFirebaseRepository {

    companion object {
        val auth = FirebaseAuth.getInstance()

        const val commonKey = "common"
        const val userKey = "user"
        const val lockerFlowerKey = "lockerFlower"

        var searchWords: List<String> = emptyList()
        var lockerFlower: List<FlowerDetail> = emptyList()
    }

    override suspend fun loadApp(): Flow<ResultWrapper<Unit>> = flow<ResultWrapper<Unit>> {

        getSearchWords().zip(getLocker()) { words, locker ->

            if (words is ResultWrapper.Success) {
                searchWords = words.data
            }

            if (locker is ResultWrapper.Success) {
                lockerFlower = locker.data
            }

            words is ResultWrapper.Success && locker is ResultWrapper.Success

        }.collect {
            if (it) emit(ResultWrapper.Success(Unit))
        }
    }.onStart { emit(ResultWrapper.Loading) }
        .flowOn(Dispatchers.IO)

    override suspend fun locker(): Flow<List<FlowerDetail>> = flow {
        emit(lockerFlower)
    }.flowOn(Dispatchers.IO)

    override suspend fun searchWords(): Flow<List<String>> = flow {
        emit(searchWords)
    }.flowOn(Dispatchers.IO)

    override suspend fun getSearchWords(): Flow<ResultWrapper<List<String>>> =
        callbackFlow {

            val db = FirebaseFirestore.getInstance()

            db.collection(commonKey)   // 작업할 컬렉션
                .get()      // 문서 가져오기
                .addOnSuccessListener { result ->
                    trySend(
                        ResultWrapper.Success(result.map { document ->
                            (document["meaning"] as String).split(",")
                        }.last())
                    )

                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    trySend(ResultWrapper.Error("오류가 발생했습니다"))
                }


            awaitClose { channel.close() }
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)

    override fun getUser(): Flow<FirebaseUser?> = flow {
        emit(auth.currentUser)
    }.flowOn(Dispatchers.IO)

    override suspend fun setLocker(flower: FlowerDetail): Flow<ResultWrapper<Unit>> =
        callbackFlow {

            val db = FirebaseFirestore.getInstance()

            auth.currentUser?.uid?.let {
                db.collection(userKey)   // 작업할 컬렉션
                    .document(it)
                    .collection(lockerFlowerKey)
                    .document(flower.dataNo.toString())
                    .set(flower)      // 문서 업데이트
                    .addOnSuccessListener { result ->
                        trySend(ResultWrapper.Success(Unit))
                    }
                    .addOnFailureListener { exception ->
                        // 실패할 경우
                        trySend(ResultWrapper.Error("오류가 발생했습니다"))
                    }
            }

            awaitClose { channel.close() }
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)

    override suspend fun getLocker(): Flow<ResultWrapper<List<FlowerDetail>>> =
        callbackFlow {

            val db = FirebaseFirestore.getInstance()


            auth.currentUser?.uid?.let {
                db.collection(userKey)   // 작업할 컬렉션
                    .document(it)
                    .collection(lockerFlowerKey)
                    .get()      // 문서 가져오기
                    .addOnSuccessListener { result ->

                        lockerFlower = result.documents.map { document ->
                            document.toObject<FlowerDetail>() ?: FlowerDetail()
                        }.toList()

                        trySend(
                            ResultWrapper.Success(
                                lockerFlower
                            )
                        )

                    }
                    .addOnFailureListener { exception ->
                        // 실패할 경우
                        trySend(ResultWrapper.Error("오류가 발생했습니다"))
                    }
            }

            awaitClose { channel.close() }
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)


}