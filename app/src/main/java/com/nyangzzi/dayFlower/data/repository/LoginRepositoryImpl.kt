package com.nyangzzi.dayFlower.data.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthBridgeActivity
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.nyangzzi.dayFlower.BuildConfig
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.PLATFORM_KAKAO
import com.nyangzzi.dayFlower.domain.model.common.PLATFORM_NAVER
import com.nyangzzi.dayFlower.domain.model.common.User
import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class LoginRepositoryImpl(
    private val context: Context
) : LoginRepository {

    companion object {
        val auth = FirebaseAuth.getInstance()
    }

    override suspend fun kaKaoLogin(): Flow<ResultWrapper<User>> = callbackFlow {

        KakaoSdk.init(context, BuildConfig.kakao_api_key_string)

        val userResult = User(
            platform = PLATFORM_KAKAO
        )

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("kakao", "카카오톡으로 로그인 실패", error)

                    trySend(ResultWrapper.Error("카카오톡으로 로그인 실패"))

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(
                        context,
                        callback = { token1, error1 ->
                            if (error1 != null) {
                                trySend(ResultWrapper.Error("카카오톡으로 로그인 실패"))
                                Log.e("kakao", "카카오계정으로 로그인 실패", error1)
                            } else if (token1 != null) {

                                UserApiClient.instance.me { user, error ->

                                    trySend(
                                        ResultWrapper.Success(
                                            userResult.copy(
                                                token = token1.accessToken,
                                                id = user?.id.toString(),
                                                nickname = user?.kakaoAccount?.profile?.nickname,
                                                profileImg = user?.kakaoAccount?.profile?.profileImageUrl,
                                                email = user?.kakaoAccount?.email
                                            )
                                        )
                                    )
                                }

                                Log.i("kakao", "카카오계정으로 로그인 성공 ${token1.accessToken}")
                            }
                        })
                } else if (token != null) {

                    UserApiClient.instance.me { user, error ->

                        trySend(
                            ResultWrapper.Success(
                                userResult.copy(
                                    token = token.accessToken,
                                    id = user?.id.toString(),
                                    nickname = user?.kakaoAccount?.profile?.nickname,
                                    profileImg = user?.kakaoAccount?.profile?.profileImageUrl,
                                    email = user?.kakaoAccount?.email
                                )
                            )
                        )
                    }
                    Log.i("kakao", "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = { token1, error1 ->
                if (error1 != null) {
                    trySend(ResultWrapper.Error("카카오톡으로 로그인 실패"))
                    Log.e("kakao", "카카오계정으로 로그인 실패", error1)
                } else if (token1 != null) {

                    UserApiClient.instance.me { user, error ->

                        trySend(
                            ResultWrapper.Success(
                                userResult.copy(
                                    token = token1.accessToken,
                                    id = user?.id.toString(),
                                    nickname = user?.kakaoAccount?.profile?.nickname,
                                    profileImg = user?.kakaoAccount?.profile?.profileImageUrl,
                                    email = user?.kakaoAccount?.email
                                )
                            )
                        )
                    }

                    Log.i("kakao", "카카오계정으로 로그인 성공 ${token1.accessToken}")
                }
            })
        }

        awaitClose { channel.close() }
    }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)

    override suspend fun naverLogin(): Flow<ResultWrapper<User>> = callbackFlow {

        NaverIdLoginSDK.initialize(
            context,
            BuildConfig.naver_client_id,
            BuildConfig.naver_client_secret,
            "하루 한 송이"
        )

        var userResult = User(
            platform = PLATFORM_NAVER
        )

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                trySend(
                    ResultWrapper.Success(
                        userResult.copy(
                            token = NaverIdLoginSDK.getAccessToken(),
                            id = result.profile?.id,
                            nickname = result.profile?.nickname,
                            profileImg = result.profile?.profileImage,
                            email = result.profile?.email
                        )
                    )
                )
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                trySend(ResultWrapper.Error("$errorDescription"))
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        val naverLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {

                userResult = userResult.copy(token = NaverIdLoginSDK.getAccessToken())

                NidOAuthLogin().callProfileApi(profileCallback)

                Log.i("naver", "네이버로 로그인 성공 ${NaverIdLoginSDK.getAccessToken()}")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                trySend(ResultWrapper.Error("$errorDescription"))
                Log.e("naver", "네이버로 로그인 실패 $errorDescription")
                //Toast.makeText(context,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
                Log.e("naver", "네이버로 로그인 실패 $message")
            }
        }

        NaverIdLoginSDK.oauthLoginCallback = naverLoginCallback

        val orientation = context.resources.configuration.orientation
        val intent = Intent(context, NidOAuthBridgeActivity::class.java).apply {
            putExtra("orientation", orientation)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        (context as? Activity)?.overridePendingTransition(0, 0)
        //NaverIdLoginSDK.authenticate(context, naverLoginCallback)

        awaitClose { channel.close() }
    }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)

    override suspend fun createFirebaseUser(user: User): Flow<ResultWrapper<Unit>> =
        callbackFlow {

            auth.createUserWithEmailAndPassword("${user.platform}_${user.email}", user.id ?: "")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Registration OK
                        //val firebaseUser = auth.currentUser
                        //firebaseUser?.uid
                        trySend(ResultWrapper.Success(Unit))

                    } else {
                        when ((task.exception as FirebaseAuthException).errorCode) {
                            "ERROR_EMAIL_ALREADY_IN_USE" -> trySend(ResultWrapper.Success(Unit))
                            else -> trySend(
                                ResultWrapper.Error(
                                    task.exception?.message ?: "오류가 발생했습니다"
                                )
                            )
                        }
                    }
                }

            awaitClose { channel.close() }
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)

    override suspend fun loginFirebaseUser(user: User): Flow<ResultWrapper<User>> = callbackFlow {

        auth.signInWithEmailAndPassword("${user.platform}_${user.email}", user.id ?: "")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(
                        ResultWrapper.Success(
                            user.copy(
                                uid = auth.currentUser?.uid
                            )
                        )
                    )
                } else {
                    trySend(ResultWrapper.Error("오류가 발생했습니다"))
                }
            }
            .addOnFailureListener { err ->
                trySend(ResultWrapper.Error(err.message ?: "오류가 발생했습니다"))
            }

        awaitClose { channel.close() }
    }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)


}