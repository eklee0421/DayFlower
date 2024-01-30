package com.nyangzzi.dayFlower.domain.model.common

const val PLATFORM_KAKAO = "kakao"
const val PLATFORM_NAVER = "naver"

data class User(
    val platform: String? = null,
    val token: String? = null,
    val id: String? = null,
    val uid: String? = null,
    val email: String? = null,
    val nickname: String? = null,
    val profileImg: String? = null,
    val introduce: String? = null
)