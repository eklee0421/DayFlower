package com.nyangzzi.dayFlower.presentation.base.util

object Utils {
    fun setImageUrl(url: String?) = Constant.ConvertImageUrl + url
    fun getUserPlatform(firebaseInfo: String?) = firebaseInfo?.substringBefore("_") ?: ""
    fun getUserEmail(firebaseInfo: String?) = firebaseInfo?.substringAfter("_") ?: ""
}