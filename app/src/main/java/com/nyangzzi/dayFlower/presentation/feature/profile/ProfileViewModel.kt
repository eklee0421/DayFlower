package com.nyangzzi.dayFlower.presentation.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.PLATFORM_KAKAO
import com.nyangzzi.dayFlower.domain.model.common.PLATFORM_NAVER
import com.nyangzzi.dayFlower.domain.usecase.firebase.GetUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.KakaoLogoutUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.LogoutFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.NaverLogoutUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.UpdateFirebaseUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateFirebaseUserUseCase: UpdateFirebaseUserUseCase,
    private val logoutFirebaseUserUseCase: LogoutFirebaseUserUseCase,
    private val naverLogoutUseCase: NaverLogoutUseCase,
    private val kakaoLogoutUseCase: KakaoLogoutUseCase
) : ViewModel() {

    private var _user = getUserUseCase()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = combine(_uiState, _user) { state, user ->
        state.copy(
            nickname = user?.displayName ?: "",
            imgUrl = user?.photoUrl.toString(),
            email = user?.email?.substringAfter("_") ?: "",
            platform = user?.email?.substringBefore("_") ?: ""
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ProfileUiState()
    )

    fun onEvent(event: ProfileEvent) {
        viewModelScope.launch {
            when (event) {
                is ProfileEvent.UpdateUserName -> updateUserName(event.newName)
                is ProfileEvent.Logout -> logout()
                is ProfileEvent.ClearToastMsg -> _uiState.update { it.copy(toastMsg = null) }
            }
        }
    }

    private suspend fun updateUserName(newName: String) {
        updateFirebaseUserUseCase(
            user = com.nyangzzi.dayFlower.domain.model.common.User(
                nickname = newName,
                profileImg = uiState.value.imgUrl
            )
        ).collect { result ->
            when (result) {
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(toastMsg = "저장에 실패했습니다") }
                }

                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(nickname = getUserUseCase().first()?.displayName ?: "")
                    }
                }

                else -> {}
            }
        }
    }

    private suspend fun logout() {
        logoutFirebaseUserUseCase().collect { result ->
            when (result) {
                is ResultWrapper.Success -> {

                    when (uiState.value.platform) {
                        PLATFORM_KAKAO -> {
                            kakaoLogoutUseCase()
                            _uiState.update { it.copy(isLogout = true, toastMsg = "로그아웃 되었습니다") }
                        }

                        PLATFORM_NAVER -> {
                            naverLogoutUseCase()
                            _uiState.update { it.copy(isLogout = true, toastMsg = "로그아웃 되었습니다") }
                        }
                    }
                }

                is ResultWrapper.Error -> _uiState.update { it.copy(toastMsg = "로그아웃에 실패했습니다") }
                else -> {}
            }
        }
    }

}