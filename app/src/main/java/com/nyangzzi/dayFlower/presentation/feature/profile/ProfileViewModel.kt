package com.nyangzzi.dayFlower.presentation.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.PLATFORM_KAKAO
import com.nyangzzi.dayFlower.domain.model.common.PLATFORM_NAVER
import com.nyangzzi.dayFlower.domain.usecase.base.OpenSourceLicenseUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.GetUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.firebase.LogoutFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.firebase.RemoveFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.firebase.UpdateFirebaseUserUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.kakao.KakaoLogoutUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.kakao.KakaoRemoveUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.naver.NaverLogoutUseCase
import com.nyangzzi.dayFlower.domain.usecase.login.naver.NaverRemoveUseCase
import com.nyangzzi.dayFlower.presentation.base.util.Utils
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
    private val kakaoLogoutUseCase: KakaoLogoutUseCase,
    private val removeFirebaseUserUseCase: RemoveFirebaseUserUseCase,
    private val kakaoRemoveUseCase: KakaoRemoveUseCase,
    private val naverRemoveUseCase: NaverRemoveUseCase,
    private val openSourceLicenseUseCase: OpenSourceLicenseUseCase
) : ViewModel() {

    private var _user = getUserUseCase()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = combine(_uiState, _user) { state, user ->
        state.copy(
            nickname = user?.displayName ?: "",
            imgUrl = if (user?.photoUrl != null) user.photoUrl.toString() else null,
            email = Utils.getUserEmail(user?.email),
            platform = Utils.getUserPlatform(user?.email)
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
                is ProfileEvent.RemoveUser -> removeUser()
                is ProfileEvent.SetShowLogoutDialog -> _uiState.update { it.copy(isLogoutDialog = event.isShown) }
                is ProfileEvent.SetShowRemoveDialog -> _uiState.update { it.copy(isRemoveDialog = event.isShown) }
                is ProfileEvent.SetPersonalDialog -> _uiState.update { it.copy(isPersonalDialog = event.isShown) }
                ProfileEvent.OpenSourceLicense -> openSourceLicenseUseCase()
                is ProfileEvent.SetProfileImg -> _uiState.update { it.copy(isSetProfileImg = event.isShown) }
                is ProfileEvent.SelectGallery -> _uiState.update { it.copy(isSelectGallery = event.isShown) }
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
                            _uiState.update {
                                it.copy(
                                    isLogoutSuccess = true,
                                    toastMsg = "로그아웃 되었습니다"
                                )
                            }
                        }

                        PLATFORM_NAVER -> {
                            naverLogoutUseCase()
                            _uiState.update {
                                it.copy(
                                    isLogoutSuccess = true,
                                    toastMsg = "로그아웃 되었습니다"
                                )
                            }
                        }
                    }
                }

                is ResultWrapper.Error -> _uiState.update { it.copy(toastMsg = "로그아웃에 실패했습니다") }
                else -> {}
            }
        }
    }

    private suspend fun removeUser() {
        removeFirebaseUserUseCase().collect { result ->
            when (result) {
                is ResultWrapper.Error -> {
                    _uiState.update {
                        it.copy(toastMsg = result.errorMessage)
                    }
                }

                is ResultWrapper.Success -> {

                    when (uiState.value.platform) {
                        PLATFORM_KAKAO -> {
                            kakaoRemoveUseCase()
                        }

                        PLATFORM_NAVER -> {
                            naverRemoveUseCase()
                        }
                    }

                    _uiState.update { it.copy(isLogoutSuccess = true, toastMsg = "이용해주셔서 감사합니다") }
                }

                else -> {}
            }
        }
    }

}