package com.nyangzzi.dayFlower.presentation.feature.profile

sealed class ProfileEvent {
    data class UpdateUserName(val newName: String) : ProfileEvent()
    object Logout : ProfileEvent()
    object RemoveUser : ProfileEvent()
    object ClearToastMsg : ProfileEvent()
}