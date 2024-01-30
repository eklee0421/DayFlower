package com.nyangzzi.dayFlower.presentation.feature.profile

sealed class ProfileEvent {
    data class UpdateUserName(val newName: String) : ProfileEvent()
}