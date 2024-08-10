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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip

class GetFirebaseRepositoryImpl : GetFirebaseRepository {

    companion object {
        val auth = FirebaseAuth.getInstance()

        const val COMMON_KEY = "common"
        const val USER_KEY = "user"
        const val LOCKER_FLOWER_KEY = "lockerFlower"

        private val _searchWords = MutableStateFlow<List<String>>(emptyList())
        val searchWords: StateFlow<List<String>> get() = _searchWords

        private val _lockerFlower = MutableStateFlow<List<FlowerDetail>>(emptyList())
        val lockerFlower: StateFlow<List<FlowerDetail>> get() = _lockerFlower


    }

    override suspend fun loadApp(): Flow<ResultWrapper<Unit>> = flow<ResultWrapper<Unit>> {

        getSearchWords().zip(getLocker()) { words, locker ->

            if (words is ResultWrapper.Success) {
                _searchWords.value = words.data
            }

            if (locker is ResultWrapper.Success) {
                _lockerFlower.value = locker.data
            }

            words is ResultWrapper.Success && locker is ResultWrapper.Success

        }.collect {
            if (it) emit(ResultWrapper.Success(Unit))
        }
    }.onStart { emit(ResultWrapper.Loading) }
        .flowOn(Dispatchers.IO)

    override fun locker(): Flow<List<FlowerDetail>> = lockerFlower

    override fun searchWords(): Flow<List<String>> = searchWords

    override suspend fun getSearchWords(): Flow<ResultWrapper<List<String>>> =
        callbackFlow {

            val db = FirebaseFirestore.getInstance()

            db.collection(COMMON_KEY)   // 작업할 컬렉션
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
                db.collection(USER_KEY)   // 작업할 컬렉션
                    .document(it)
                    .collection(LOCKER_FLOWER_KEY)
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

    override suspend fun removeLocker(dataNo: Int): Flow<ResultWrapper<Unit>> =
        callbackFlow {

            val db = FirebaseFirestore.getInstance()

            auth.currentUser?.uid?.let {
                db.collection(USER_KEY)   // 작업할 컬렉션
                    .document(it)
                    .collection(LOCKER_FLOWER_KEY)
                    .document(dataNo.toString())
                    .delete()     // 문서 업데이트
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
                db.collection(USER_KEY)   // 작업할 컬렉션
                    .document(it)
                    .collection(LOCKER_FLOWER_KEY)
                    .get()      // 문서 가져오기
                    .addOnSuccessListener { result ->

                        _lockerFlower.value = result.documents.map { document ->
                            document.toObject<FlowerDetail>() ?: FlowerDetail()
                        }.toList()

                        trySend(
                            ResultWrapper.Success(
                                lockerFlower.value
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

    override fun checkIsSaved(dataNo: Int): Flow<Boolean> = flow {
        emit(
            lockerFlower.value.any { it.dataNo == dataNo }
        )
    }.flowOn(Dispatchers.IO)


}