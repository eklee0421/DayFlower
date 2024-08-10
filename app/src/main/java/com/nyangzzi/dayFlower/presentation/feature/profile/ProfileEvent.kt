package com.nyangzzi.dayFlower.presentation.feature.profile

sealed class ProfileEvent {
    data class UpdateUserName(val newName: String) : ProfileEvent()
    object Logout : ProfileEvent()
    object RemoveUser : ProfileEvent()
    object ClearToastMsg : ProfileEvent()
    object OpenSourceLicense : ProfileEvent()
    data class SetShowRemoveDialog(val isShown: Boolean) : ProfileEvent()
    data class SetPersonalDialog(val isShown: Boolean) : ProfileEvent()
    data class SetShowLogoutDialog(val isShown: Boolean) : ProfileEvent()
    data class SetProfileImg(val isShown: Boolean) : ProfileEvent()
    data class SelectGallery(val isShown: Boolean) : ProfileEvent()
}