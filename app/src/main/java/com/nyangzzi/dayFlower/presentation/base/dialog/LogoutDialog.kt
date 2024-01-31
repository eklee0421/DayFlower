package com.nyangzzi.dayFlower.presentation.base.dialog

import androidx.compose.runtime.Composable

@Composable
fun LogoutDialog(isShow: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {

    if (isShow) {
        MessageDialog(
            title = "로그아웃 하시겠습니까?",
            confirmText = "로그아웃", onConfirm = onConfirm, onDismiss = onDismiss
        )
    }
}